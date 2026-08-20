package com.Distributedsystem.DFSS.repository;

import com.Distributedsystem.DFSS.entity.StorageNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageNodeRepository extends JpaRepository<StorageNode, String> {
}