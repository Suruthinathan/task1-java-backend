# 🧩 Task 1 — Java Backend REST API

## 👩‍💻 Author
**Name:** Suruthi M S  
**Date:** October 2025  

---

## 📘 Overview
This project implements a **Java Spring Boot REST API** for managing and executing shell-based tasks.  
Each task contains:
- `id`
- `name`
- `owner`
- `command`
- A list of past `TaskExecutions` (start time, end time, and output).

The API stores data in **MongoDB** and allows CRUD operations (Create, Read, Update, Delete), along with executing commands safely inside a controlled environment.

---

## ⚙️ Requirements

| Tool | Version | Purpose |
|------|----------|----------|
| **Java** | 17+ | For running the Spring Boot application |
| **Maven** | 3.8+ | To build and run the app |
| **MongoDB** | 6.x (Docker) | Database backend |
| **Postman / curl** | latest | For testing API endpoints |

---

## 🐳 Step 1 — Run MongoDB in Docker

Open PowerShell or VS Code terminal and run:

```bash
docker run -d --name tasks-mongo -p 27017:27017 \
-e MONGO_INITDB_DATABASE=tasksdb \
-v mongo-data:/data/db mongo:6
'''

🚀 Running the Application

1️⃣ Clone or download the repository

git clone https://github.com/<your-username>/<your-task1-repo-name>.git
cd <your-task1-repo-name>


2️⃣ Build the project using Maven

mvn clean package

3️⃣ Run the Spring Boot application

mvn spring-boot:run


By default, the application will start at:
👉 http://localhost:8080

| Method   | Endpoint          | Description               |
| -------- | ----------------- | ------------------------- |
| `POST`   | `/api/items`      | Create a new record       |
| `GET`    | `/api/items`      | Get all records           |
| `GET`    | `/api/items/{id}` | Get a record by ID        |
| `PUT`    | `/api/items/{id}` | Update an existing record |
| `DELETE` | `/api/items/{id}` | Delete a record by ID     |


🧰 Example Requests (via curl)
➕ Create Record

curl -X POST http://localhost:8080/api/items \
-H "Content-Type: application/json" \
-d '{"name":"Sensor Module","type":"Vibration","value":12.5}'

📋 Get All Records
curl -X GET http://localhost:8080/api/items

🔍 Get by ID
curl -X GET http://localhost:8080/api/items/670f92d8c18f4a2b7f3e2a9d

✏️ Update Record
curl -X PUT http://localhost:8080/api/items/670f92d8c18f4a2b7f3e2a9d \
-H "Content-Type: application/json" \
-d '{"name":"Sensor Module","type":"Acoustic","value":15.9}'

❌ Delete Record
curl -X DELETE http://localhost:8080/api/items/670f92d8c18f4a2b7f3e2a9d

📂 Project Structure

Task1/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/task1/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── Task1Application.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│
├── pom.xml
├── README.md
└── screenshots/
    ├── input.png
    └── output.png

📸 Screenshots

Below are screenshots showing input and output (stored in screenshots/ folder):

| Description                      | Screenshot                                  |
| -------------------------------- | ------------------------------------------- |
| Application Running              | ![App Running](screenshots/app_running.png) |
| Postman Request (Create Item)    | ![Create](screenshots/create_item.png)      |
| Postman Response (Get All Items) | ![GetAll](screenshots/get_all.png)          |


🧾 Notes

Ensure MongoDB is running before starting the app.

Use the default port 8080 unless changed in application.properties.

Replace <your-username> and <your-task1-repo-name> with your actual GitHub repo details.

Screenshots should show your system date/time and your name to confirm originality.

🧑‍💻 Author

Suruthi M S
GitHub: https://github.com/<your-username>

Email: your-email@example.com