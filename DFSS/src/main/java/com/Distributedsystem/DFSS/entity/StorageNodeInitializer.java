package com.Distributedsystem.DFSS.entity;

import com.Distributedsystem.DFSS.entity.StorageNode;
import com.Distributedsystem.DFSS.repository.StorageNodeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StorageNodeInitializer implements CommandLineRunner {

    private final StorageNodeRepository storageNodeRepository;

    public StorageNodeInitializer(StorageNodeRepository storageNodeRepository) {
        this.storageNodeRepository = storageNodeRepository;
    }

    @Override
    public void run(String... args) {

        if (storageNodeRepository.count() == 0) {

            StorageNode node1 = new StorageNode();
            node1.setNodeId("Node1");
            node1.setNodeName("Storage Node 1");
            node1.setTotalStorage(10L * 1024 * 1024 * 1024); // 10 GB
            node1.setUsedStorage(0);
            node1.setOnline(true);

            StorageNode node2 = new StorageNode();
            node2.setNodeId("Node2");
            node2.setNodeName("Storage Node 2");
            node2.setTotalStorage(10L * 1024 * 1024 * 1024);
            node2.setUsedStorage(0);
            node2.setOnline(true);

            StorageNode node3 = new StorageNode();
            node3.setNodeId("Node3");
            node3.setNodeName("Storage Node 3");
            node3.setTotalStorage(10L * 1024 * 1024 * 1024);
            node3.setUsedStorage(0);
            node3.setOnline(true);

            storageNodeRepository.save(node1);
            storageNodeRepository.save(node2);
            storageNodeRepository.save(node3);
        }
    }
}