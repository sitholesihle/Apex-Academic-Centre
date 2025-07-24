# Apex-Academic-Centre

This is a full-stack web application built for Apex Academic Centre, allowing users to book tutoring sessions, apply to become tutors, and access academic programs such as Matric Rewrite and Maths Classes.

### 🌐 Live Site
Visit the application here:  
➡️ [bookatutorapexacademiccentre.co.za](https://bookatutorapexacademiccentre.co.za/)

---

## 🚀 Features

- Book a tutor online
- Tutor application form
- Matric rewrite program details
- Maths class information
- Admin and backend management

---

## 🛠️ Tech Stack

- **Backend:** Spring Boot (Java)
- **Frontend:** Thymeleaf (HTML/CSS/JavaScript)
- **Database:** MySQL
- **Hosting:** [Heroku](https://heroku.com)

---

## 📂 Project Structure (High-level)

src/
├── main/
│ ├── java/
│ │ └── com.example.app/ # Java source files
│ └── resources/
│ ├── templates/ # Thymeleaf templates
│ ├── static/ # Static files (CSS, JS)
│ └── application.properties # Spring Boot config
└── test/ # Unit and integration tests

---

## ⚙️ Setup & Run Locally

### Prerequisites

- Java 17+
- Maven
- Git

### Steps

```bash
# Clone the repository
git clone https://github.com/sithole-sihle/apex-academic-centre.git
cd your-repo

# Run the app
./mvnw spring-boot:run
Visit http://localhost:1234 in your browser.
