package com.Distributedsystem.DFSS.algorithms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class MergeAlgorithm {

    @Value("${total.nodes}")
    private int TOTAL_NODES;

    public void mergeFiles(int totalParts, String outputFilePath, int fileId) throws IOException {
        File outputFile = new File(outputFilePath);
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }
        try (BufferedOutputStream out =
                     new BufferedOutputStream(new FileOutputStream(outputFile))) {
            for (int part = 1; part <= totalParts; part++) {
                File chunk = findPart(part, fileId);

                try (BufferedInputStream in =
                             new BufferedInputStream(new FileInputStream(chunk))) {

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }

    private File findPart(int partNumber, int fileId) {

        int primaryNode = ((partNumber - 1) % TOTAL_NODES) + 1;

        int replicaNode = primaryNode + 1;
        if (replicaNode > TOTAL_NODES) {
            replicaNode = 1;
        }

        File primary = new File(
                "DFSS/Storage/Node" + primaryNode + "/" +
                        fileId + "-part-" + partNumber
        );

        if (primary.exists()) {
            return primary;
        }

        File replica = new File(
                "DFSS/Storage/Node" + replicaNode + "/" +
                        fileId + "-part-" + partNumber
        );

        if (replica.exists()) {
            return replica;
        }

        throw new RuntimeException(
                "Part " + partNumber + " not found on primary or replica node."
        );
    }
}