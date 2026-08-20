package com.Distributedsystem.DFSS.repository;

import com.Distributedsystem.DFSS.entity.FileEntity;
import com.Distributedsystem.DFSS.entity.FilePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}