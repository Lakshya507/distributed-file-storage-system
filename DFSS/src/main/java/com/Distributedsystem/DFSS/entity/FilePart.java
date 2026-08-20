package com.Distributedsystem.DFSS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "file_parts")
public class FilePart {

    @Id
    private String id;

    private String fileId;

    private int partNumber;

    private String nodeId;

    private String partPath;

    public FilePart() {
    }

    public FilePart(String id, String fileId, int partNumber, String nodeId, String partPath) {
        this.id = id;
        this.fileId = fileId;
        this.partNumber = partNumber;
        this.nodeId = nodeId;
        this.partPath = partPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPartPath() {
        return partPath;
    }

    public void setPartPath(String partPath) {
        this.partPath = partPath;
    }

    @Override
    public String toString() {
        return "FilePart{" +
                "id='" + id + '\'' +
                ", fileId='" + fileId + '\'' +
                ", partNumber=" + partNumber +
                ", nodeId='" + nodeId + '\'' +
                ", partPath='" + partPath + '\'' +
                '}';
    }
}