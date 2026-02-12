# Adaptive Quiz Master  - By Kisalay

An AI-powered adaptive quiz system for online education, tackling pain points like personalized learning and instant feedback. Built in Pimpri, Maharashtra, IN, for the Spring Boot Cohort 4.0 Hackathon.

## Theme
AI-Powered Online Education (adaptive quizzes, AI tutors, skill trackers).

## Features
- Core: Spring Boot 4.0 + Spring AI with Ollama for local AI.
- Auth: JWT for students/teachers.
- AI: Question generation, answer evaluation, personalization via embeddings.
- Scalability: Redis caching, RabbitMQ async, pgvector for semantic search.
- Frontend: Thymeleaf UI for easy interaction.
- Extras: Recommendations, tests, Docker deployment.

## Tech Stack
- Backend: Spring Boot 4.0, Spring AI, JPA, PostgreSQL (pgvector).
- AI: Ollama (llama3.2, nomic-embed-text).
- Security: Spring Security + JWT.
- Scalability: Redis, RabbitMQ.
- Frontend: Thymeleaf.
- Tools: Swagger, Docker.

## Architecture
- User --> Frontend/REST --> Security Filter --> Controllers --> Services (AI/Async/Cache) --> Repos (Vector DB) --> External (Ollama, Redis, RabbitMQ).

## Setup & Run Locally
1. Install Java 21, Maven, PostgreSQL (run `CREATE EXTENSION vector;` in quizdb).
2. Start services: Docker for Redis/RabbitMQ/Ollama as per instructions.
3. Update application.yml 
4. Seed DB: Run app once to apply data.sql.
5. `mvn spring-boot:run`.
6. Access: http://localhost:8080/ (frontend), /swagger-ui.html (APIs).
7. Register/login as teacher/student.

## Deployment (Extra Credit)
- Build JAR: `mvn package`.
- Docker: `docker build -t adaptive-quiz-master .`
- Run: `docker-compose up` (starts app + DB + Redis + RabbitMQ).
- Deploy to Render.com: Create web service, link GitHub repo, add env vars from application.yml.

## Decisions & Innovations
- Used pgvector for semantic question recs to personalize learning (e.g., suggest similar questions for weak areas).
- Async eval with RabbitMQ for scalability (handles high traffic without blocking).
- Local Ollama for cost-free AI, with fallback parsing.
- Thymeleaf for quick frontend without JS framework.

## Limitations & Future
- No mobile UI yet.
- Add cloud AI (OpenAI) option.

## Deliverables
- Deployed:
- Design: architecture.png (draw.io export).
- Swagger: Integrated.
- Demo Video: 4-min walkthrough (login, generate question, take quiz, see recs) 

