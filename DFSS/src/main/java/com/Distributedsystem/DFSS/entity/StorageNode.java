package com.Distributedsystem.DFSS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "storage_nodes")
public class StorageNode {

    @Id
    private String nodeId;

    private String nodeName;

    private long totalStorage;

    private long usedStorage;

    private boolean online;

    public StorageNode() {
    }

    public StorageNode(String nodeId, String nodeName, long totalStorage, long usedStorage, boolean online) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.totalStorage = totalStorage;
        this.usedStorage = usedStorage;
        this.online = online;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public long getTotalStorage() {
        return totalStorage;
    }

    public void setTotalStorage(long totalStorage) {
        this.totalStorage = totalStorage;
    }

    public long getUsedStorage() {
        return usedStorage;
    }

    public void setUsedStorage(long usedStorage) {
        this.usedStorage = usedStorage;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "StorageNode{" +
                "nodeId='" + nodeId + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", totalStorage=" + totalStorage +
                ", usedStorage=" + usedStorage +
                ", online=" + online +
                '}';
    }
}