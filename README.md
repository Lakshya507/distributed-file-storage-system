<div align="center">

# 🚀 Distributed File Storage System (DFSS)

### A Fault-Tolerant Distributed Storage System built with **Java • Spring Boot • PostgreSQL**

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

*A scalable distributed storage solution that splits files into chunks, distributes them across storage nodes, replicates data for fault tolerance, and reconstructs files seamlessly during download.*

</div>

---

# ✨ Features

- 📤 Upload large files
- 🧩 Automatic **1 MB chunking**
- 🌐 Distributed storage across multiple nodes
- 🔄 Chunk-level replication
- 🛡️ Fault-tolerant recovery
- 📥 Merge chunks during download
- 🗑️ Delete files and all replicated chunks
- 🗄️ PostgreSQL metadata management
- ⚡ RESTful APIs
- 📊 Storage node management

---

# 🏗️ System Architecture

```text
                     ┌────────────────────┐
                     │       Client       │
                     └─────────┬──────────┘
                               │
                         REST API Request
                               │
                               ▼
                ┌──────────────────────────────┐
                │       Master Server          │
                │      Spring Boot API         │
                └──────────────┬───────────────┘
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
          ▼                    ▼                    ▼
 ┌────────────────┐   ┌────────────────┐   ┌────────────────┐
 │ Storage Node 1 │   │ Storage Node 2 │   │ Storage Node 3 │
 └────────────────┘   └────────────────┘   └────────────────┘
          │                    │                    │
          └──────────────Stores File Chunks─────────┘

                    PostgreSQL Metadata Database
```

---

# ⚙️ Tech Stack

| Technology | Usage |
|------------|-------|
| ☕ Java 21 | Backend Development |
| 🌱 Spring Boot | REST APIs |
| 🐘 PostgreSQL | Metadata Storage |
| 🗃️ Spring Data JPA | ORM |
| 📦 Maven | Dependency Management |
| 📂 Java File I/O | Chunk Storage |

---

# 📤 Upload Flow

```text
Client
   │
   ▼
Upload File
   │
   ▼
Temporary Storage
   │
   ▼
Split into 1 MB Chunks
   │
   ▼
Distribute Across Nodes
   │
   ▼
Create Replica
   │
   ▼
Store Metadata in PostgreSQL
   │
   ▼
Delete Temporary File
```

---

# 📥 Download Flow

```text
Client
   │
   ▼
Request File
   │
   ▼
Fetch Metadata
   │
   ▼
Locate Chunks
   │
   ▼
Primary Missing?
 ┌──────┴──────┐
 │             │
No            Yes
 │             │
 ▼             ▼
Read        Read Replica
 │             │
 └──────┬──────┘
        ▼
 Merge Chunks
        ▼
Return File
```

---

# 🔄 Replication

Every chunk has **two copies** stored on different nodes.

| Chunk | Primary | Replica |
|--------|---------|----------|
| Part 1 | Node 1 | Node 2 |
| Part 2 | Node 2 | Node 3 |
| Part 3 | Node 3 | Node 1 |

If one node fails, the system automatically retrieves the replica.

---

# 🛡️ Fault Tolerance

```text
          Node 2
             ❌
             │
             ▼
Primary Chunk Missing
             │
             ▼
Replica Found
             │
             ▼
Continue Download
             │
             ▼
 User Receives File Successfully ✅
```

---

# 🗄️ Database Schema

## 📁 files

| Column | Description |
|---------|-------------|
| id | File ID |
| file_name | Original File Name |
| file_size | File Size |
| total_parts | Number of Chunks |
| uploaded_at | Upload Time |

---

## 📦 file_parts

| Column | Description |
|---------|-------------|
| id | Chunk ID |
| file_id | File ID |
| part_number | Chunk Number |
| node_id | Storage Node |
| part_path | Physical Chunk Path |

---

## 🖥️ storage_nodes

| Column | Description |
|---------|-------------|
| node_id | Node ID |
| node_name | Node Name |
| total_storage | Total Capacity |
| used_storage | Used Capacity |
| online | Node Status |

---

# 📡 REST APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/files/upload` | Upload File |
| GET | `/api/files` | Get All Files |
| GET | `/api/files/{id}` | Get File By ID |
| GET | `/api/files/download/{id}` | Download File |
| DELETE | `/api/files/{id}` | Delete File |
| GET | `/api/files/nodes` | Get Storage Nodes |

---

# 📂 Project Structure

```text
src
│
├── controller
│
├── entity
│
├── repository
│
├── service
│
├── algorithms
│   ├── ChunkAlgorithm
│   └── MergeAlgorithm
│
├── config
│
└── resources
```

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/<your-username>/distributed-file-storage-system.git
```

## Configure PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dfss
spring.datasource.username=postgres
spring.datasource.password=your_password
```

## Run Application

```bash
mvn spring-boot:run
```

---

# 🎯 Future Enhancements

- ❤️ Heartbeat Monitoring
- 🔁 Automatic Re-Replication
- ⚖️ Smart Load Balancing
- 🔐 Authentication & Authorization
- 🐳 Docker Deployment
- ☁️ Cloud Storage Integration
- 📈 Monitoring Dashboard
- 🔍 SHA-256 Checksum Validation

---

# 📚 What I Learned

- Distributed Systems
- Chunk-Based Storage
- Replication
- Fault Tolerance
- Metadata Management
- Spring Boot
- PostgreSQL
- Java File I/O
- REST API Design
- Backend System Design

---

<div align="center">

## ⭐ If you found this project interesting, consider giving it a star!

**Built with ❤️ using Java, Spring Boot & PostgreSQL**

</div>
