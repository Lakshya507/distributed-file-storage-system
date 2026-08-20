package com.Distributedsystem.DFSS.algorithms;

import com.Distributedsystem.DFSS.entity.FileEntity;
import com.Distributedsystem.DFSS.entity.FilePart;
import com.Distributedsystem.DFSS.repository.FilePartRepository;
import com.Distributedsystem.DFSS.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FilePartRepository filePartRepository;

    @Transactional
    public void deleteFile(Integer fileId) throws IOException {

        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        List<FilePart> parts = filePartRepository.findByFileId(String.valueOf(fileId));

        // Delete physical chunk files
        for (FilePart part : parts) {
            Path path = Paths.get(part.getPartPath());
            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Deleted: " + path);
            }
        }

        // Delete chunk records
        filePartRepository.deleteAll(parts);

        // Delete file metadata
        fileRepository.delete(file);
    }
}