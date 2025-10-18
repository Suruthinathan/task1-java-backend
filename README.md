# Task 1 — Java Task Runner API

## 🧩 Overview
This project is a **Spring Boot REST API** that lets users create, store, and execute tasks.  
Each task contains:
- An ID, name, owner, and shell command.
- A list of all previous executions (`TaskExecutions`), with timestamps and output.  

The project demonstrates **Java backend development**, **MongoDB integration**, and **Docker-based setup**.

---

## ⚙️ Requirements
| Component | Version / Tool |
|------------|----------------|
| Java | 17 |
| Maven | 3.8+ |
| MongoDB | 6 (using Docker) |
| Tools | Postman or curl |

---

## 🐳 Run MongoDB Using Docker

Start MongoDB in Docker:

```bash
docker run -d --name tasks-mongo -p 27017:27017 \
  -e MONGO_INITDB_DATABASE=tasksdb \
  -v mongo-data:/data/db mongo:6
