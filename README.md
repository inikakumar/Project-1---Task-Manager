# 📝 Task Manager - Full Stack Application

A full-stack Task Management application built with **Spring Boot** (backend) and **React + Vite** (frontend). Users can create, track, filter, and manage tasks with priorities and due dates.

## 🚀 Features

- **CRUD Operations:** Create, Read, Update (Mark as Complete), and Delete tasks.
- **Priority Levels:** Assign `LOW`, `MEDIUM`, or `HIGH` priority with color-coded badges.
- **Smart Sorting:** Tasks sorted by Priority (High to Low), then earliest Due Date.
- **Dynamic Filtering:** Filter by completion status (Active/Completed) and priority level.
- **Validation & Error Handling:** Frontend and backend validation with clear error messages.
- **Responsive UI:** Clean, modern interface built with custom CSS.

## 🛠️ Tech Stack

**Backend:**
- Java 17+
- Spring Boot 3 (Web, Data JPA)
- Hibernate
- Maven

**Frontend:**
- React + Vite
- Axios
- Custom CSS

## 📂 Project Structure

```text
Project-1---Task-Manager/
├── src/main/java/...          # Spring Boot backend source
├── pom.xml                    # Maven build file
├── mvnw / mvnw.cmd            # Maven wrapper
└── task_manager_frontend/     # React + Vite frontend
    ├── src/
    │   ├── App.jsx
    │   └── App.css
    └── package.json
```

## ⚙️ Getting Started

### Prerequisites
- Java 17+ and Maven
- Node.js and npm

### 1. Run the Backend (from project root)

```bash
./mvnw spring-boot:run
```

Backend runs on `http://localhost:8080`.

### 2. Run the Frontend (second terminal)

```bash
cd task_manager_frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`.

## 👩‍💻 Author

**Inika Kumar**
- GitHub: [@inikakumar](https://github.com/inikakumar)
