package com.Distributedsystem.DFSS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    private Integer id;

    private String fileName;
    private long fileSize;
    private int totalParts;
    private LocalDateTime uploadedAt;

    public FileEntity() {
    }

    public FileEntity(Integer id, String fileName, long fileSize, int totalParts, LocalDateTime uploadedAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.totalParts = totalParts;
        this.uploadedAt = uploadedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getTotalParts() {
        return totalParts;
    }

    public void setTotalParts(int totalParts) {
        this.totalParts = totalParts;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


}