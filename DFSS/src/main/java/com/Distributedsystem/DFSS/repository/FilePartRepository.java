package com.Distributedsystem.DFSS.repository;

import com.Distributedsystem.DFSS.entity.FilePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilePartRepository extends JpaRepository<FilePart, String> {

    List<FilePart> findByFileId(String fileId);

    void deleteByFileId(String fileId);
}