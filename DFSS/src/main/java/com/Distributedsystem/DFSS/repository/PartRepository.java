package com.Distributedsystem.DFSS.repository;

import com.Distributedsystem.DFSS.entity.FilePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<FilePart, Integer> {
}