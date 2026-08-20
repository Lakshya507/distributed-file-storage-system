package com.Distributedsystem.DFSS.algorithms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkAlgorithm {
    private static final int CHUNK_SIZE = 1024*1024;
    // For 1 Megabytes( MB )

//    private static final int TOTAL_NODES = 3;
    @Value("${total.nodes}")
    private int TOTAL_NODES;
    private static String outputDir = "/home/frog/Documents/Distributed System/DFSS (1)/DFSS/Storage/Node";


    public class PartInfo {

        private int partNumber;

        private String primaryNode;
        private String replicaNode;

        private String primaryPath;
        private String replicaPath;

        public PartInfo(int partNumber,
                        String primaryNode,
                        String replicaNode,
                        String primaryPath,
                        String replicaPath) {

            this.partNumber = partNumber;
            this.primaryNode = primaryNode;
            this.replicaNode = replicaNode;
            this.primaryPath = primaryPath;
            this.replicaPath = replicaPath;
        }

        public int getPartNumber() {
            return partNumber;
        }

        public String getPrimaryNode() {
            return primaryNode;
        }

        public String getReplicaNode() {
            return replicaNode;
        }

        public String getPrimaryPath() {
            return primaryPath;
        }

        public String getReplicaPath() {
            return replicaPath;
        }
    }

    public List<PartInfo> splitFile(String inputFile, int id /* String outputDir */) throws IOException {
        File file = new File(inputFile);
        try(InputStream in = new FileInputStream(file)){
            byte[] buffer = new byte[CHUNK_SIZE];
            int partnumber  = 1;
            int byteReader = in.read(buffer);
            int node = 1;
            List<PartInfo> parts = new ArrayList<>();
            while (byteReader != -1) {

                // Primary node
                int primaryNode = node;

                // Replica node
                int replicaNode = node + 1;
                if (replicaNode > TOTAL_NODES) {
                    replicaNode = 1;
                }

                String primaryDir = outputDir + primaryNode;
                String replicaDir = outputDir + replicaNode;

                new File(primaryDir).mkdirs();
                new File(replicaDir).mkdirs();

                String fileName = id + "-part-" + partnumber;

                String primaryPath = primaryDir + "/" + fileName;
                String replicaPath = replicaDir + "/" + fileName;

                // Write primary copy
                try (OutputStream out = new FileOutputStream(primaryPath)) {
                    out.write(buffer, 0, byteReader);
                }

                // Write replica copy
                try (OutputStream out = new FileOutputStream(replicaPath)) {
                    out.write(buffer, 0, byteReader);
                }

                parts.add(new PartInfo(
                        partnumber,
                        "Node" + primaryNode,
                        "Node" + replicaNode,
                        primaryPath,
                        replicaPath
                ));

                node++;
                if (node > TOTAL_NODES) {
                    node = 1;
                }

                partnumber++;
                byteReader = in.read(buffer);
            }
            return parts;
        }


    }
}
