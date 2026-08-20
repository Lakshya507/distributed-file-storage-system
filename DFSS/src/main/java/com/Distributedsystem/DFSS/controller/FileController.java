package com.Distributedsystem.DFSS.controller;

import com.Distributedsystem.DFSS.algorithms.ChunkAlgorithm;
import com.Distributedsystem.DFSS.algorithms.FileService;
import com.Distributedsystem.DFSS.algorithms.MergeAlgorithm;
import com.Distributedsystem.DFSS.entity.FileEntity;
import com.Distributedsystem.DFSS.entity.FilePart;
import com.Distributedsystem.DFSS.entity.StorageNode;
import com.Distributedsystem.DFSS.repository.FilePartRepository;
import com.Distributedsystem.DFSS.repository.FileRepository;
import com.Distributedsystem.DFSS.repository.StorageNodeRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {


    private static final int CHUNK_SIZE = 1024*1024;


    @Autowired
    private ChunkAlgorithm chunkAlgorithm;

    @Autowired
    private FilePartRepository filePartRepository;

    @Autowired
    private MergeAlgorithm mergeAlgorithm;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;


    @Value("${storage.path}")
    private String STORAGE_DIR   ;

    @Value("${total.nodes}")
    private int TOTAL_NODES;
    private static int id = 1;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadfile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {

            // Create storage directory if it doesn't exist
            File dir = new File(STORAGE_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Save uploaded file temporarily
            String originalFileName = file.getOriginalFilename();
            File destinationFile = new File(STORAGE_DIR + originalFileName);
            file.transferTo(destinationFile);

            System.out.println("File uploaded successfully.");

            int fileId = id++;

            // Split file
            List<ChunkAlgorithm.PartInfo> parts =
                    chunkAlgorithm.splitFile(STORAGE_DIR + originalFileName, fileId);

            // Save file metadata
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(fileId);
            fileEntity.setFileName(originalFileName);
            fileEntity.setFileSize(destinationFile.length());
            fileEntity.setUploadedAt(LocalDateTime.now());

            int totalParts = parts.size();
            fileEntity.setTotalParts(totalParts);

            fileRepository.save(fileEntity);

            // Save every chunk into file_parts table
            for (ChunkAlgorithm.PartInfo part : parts) {

                // Save primary copy
                FilePart primary = new FilePart();
                primary.setId(java.util.UUID.randomUUID().toString());
                primary.setFileId(String.valueOf(fileId));
                primary.setPartNumber(part.getPartNumber());
                primary.setNodeId(part.getPrimaryNode());
                primary.setPartPath(part.getPrimaryPath());

                filePartRepository.save(primary);

                // Save replica copy
                FilePart replica = new FilePart();
                replica.setId(java.util.UUID.randomUUID().toString());
                replica.setFileId(String.valueOf(fileId));
                replica.setPartNumber(part.getPartNumber());
                replica.setNodeId(part.getReplicaNode());
                replica.setPartPath(part.getReplicaPath());

                filePartRepository.save(replica);
            }

            // Delete temporary uploaded file
            Files.delete(Paths.get(STORAGE_DIR + originalFileName));

            return ResponseEntity.ok("File uploaded successfully: " + originalFileName);

        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }


    //Download

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) throws IOException {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));


        String outputPath = STORAGE_DIR + File.separator + file.getFileName();

        System.out.println("Total Parts: " + file.getTotalParts());
        // Merge all chunks
        mergeAlgorithm.mergeFiles(
                file.getTotalParts(),
                outputPath,
                fileId
        );

        // Load merged file
        Path path = Paths.get(outputPath);

        if (!Files.exists(path)) {
            throw new RuntimeException("Merged file not found");
        }

        Resource resource = new UrlResource(path.toUri());

        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header("Content-Disposition",
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .contentLength(Files.size(path))
                .body(resource);
    }



    //

    @GetMapping
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }


    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Integer fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to delete file.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable Integer id) {

        return fileRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Autowired
    private StorageNodeRepository storageNodeRepository;

    @GetMapping("/nodes")
    public List<StorageNode> getAllNodes() {
        return storageNodeRepository.findAll();
    }
}

