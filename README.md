# RUKfigure

RUKfigure is a Java Spring Boot project for managing a collection of collectible figures. It provides a web-based interface for administrators to add, edit, and delete figure data, making figure management easy and organized.

## Features

- Admin can create, read, update, and delete figures.
- Organized storage of figure information (name, type, series, etc.).
- Simple and clean Spring Boot backend.
- Ready for frontend integration.

## Tech Stack

- **Backend:** Java, Spring Boot
- **Frontend:** (Optional) Angular or React
- **Database:** MySQL or PostgreSQL
- **Version Control:** Git/GitHub



---

## 🚀 Key Features
- **Admin Panel**: Manage figures, comics, and admins (create, edit, delete, search)  
- **Statistics Dashboard**: Summary cards + charts for data insights  
- **Auth & Security**: JWT-based login, passwords encrypted with BCrypt  
- **Audit Logging**: Tracks operations (e.g., UPDATE/DELETE) via ELK Stack  
- **RESTful API**: Clean separation of Controller–Service–Repository layers  
- **Responsive UI**: Built with React + Ant Design for usability  

---

## 🛠 Architecture & Tech Stack
- **Frontend**: React, Ant Design, Axios  
- **Backend**: Spring Boot (Java), Spring Security, JPA/Hibernate  
- **Database**: MySQL  
- **Logging/Monitoring**: ELK (Elasticsearch, Logstash, Kibana)  
- **Version Control**: Git/GitHub  

---

## 📂 Core Data Models
- `Admin(id, email, username, password[bcrypt])`  
- `Figure(...)`, `Family(...)` (extendable fields based on domain)  

---

## 📡 Example Endpoints
- `POST /api/auth/login` → returns JWT  
- `GET /api/admins` / `POST /api/admins`  
- `GET /api/figures` / `POST /api/figures` (etc.)  

---

## ☁️ Deployment Options
- **Azure App Service** (Spring Boot JAR) + **Static Web (Azure Storage)** for React  
- Or **Azure VM (Linux)** with Java/MySQL installed and running as a service  
- **Azure Monitor/Log Analytics** for telemetry  

---

## 📌 Current Status
- Backend + Security + JPA functional, passwords migrated to BCrypt  
- Admin Dashboard working (statistics + CRUD tables)  
- Audit logs integrated with ELK  
- Code available on GitHub (**main** branch)  

---

## 🗺 Roadmap
- [ ] Deploy to Azure (App Service or VM)  
- [ ] Configure domain & HTTPS  
- [ ] Add Viewer-only pages (SEO-friendly)  
- [ ] Set up CI/CD (GitHub Actions)  
- [ ] Add unit/integration tests and API documentation


## Getting Started

1. Clone this repository:
   ```bash
   git clone https://github.com/24Zen/RUKfigure.git
