
# 🚀 ThrottleX – API Rate Limiting Gateway

ThrottleX is a production-grade, microservice-based **API Gateway** built with **Java 17** and **Spring Boot**, implementing **three industry-standard rate-limiting strategies**:  
🔒 Fixed Window, ⏳ Sliding Log, and 🪙 Token Bucket.

The project simulates real-world API management by integrating authentication, monitoring, logging, distributed state management, and horizontal scalability using Docker and Nginx.

---

## 🔧 Features

- ✅ **Rate Limiting Strategies**:
  - Fixed Window
  - Sliding Log
  - Token Bucket

- 🔐 **JWT-based Authentication**

- ⚙️ **Distributed Quota Tracking** using **Redis**

- 📊 **Real-Time Monitoring** using **Prometheus + Spring Boot Actuator**

- 🧾 **Persistent Request Logging** via Audit Logger

- 🐳 **Dockerized Microservices** with **Nginx Load Balancing**

---

## 📂 Project Structure

```

throttlex/
├── src/main/java/com/throttlex/   # Source Code
├── Dockerfile                     # Java service image
├── docker-compose.yml             # Multi-service orchestration
├── nginx.conf                     # Load balancing config
├── wait-for-redis.sh              # Redis readiness script
└── README.md

````

---

## 🧪 API Endpoints

| Method | Endpoint            | Description                            | Auth Required  |
|--------|---------------------|----------------------------------------|----------------|
| GET    | `/token?userId=...` | Generate JWT for user                  | ❌ No          |
| POST   | `/api/track`        | Track a request with rate limiting     | ✅ Yes         |

📌 **Headers**:
```http
Authorization: Bearer <JWT_TOKEN>
````

---

## 🐳 Getting Started (Docker)

### 1. Build & Run

```bash
docker-compose up --build
```

### 2. Generate Token

```bash
curl http://localhost:8080/token?userId=user_free
```

### 3. Make Authenticated Request

```bash
curl -X POST http://localhost:8080/api/track \
  -H "Authorization: Bearer <PASTE_JWT_HERE>"
```

---

## 📊 Prometheus Integration

Spring Boot Actuator exposes metrics at:

```
http://localhost:8081/actuator/prometheus
```

---

## 🛠️ Tech Stack

* Java 17, Spring Boot
* Spring Security + JWT
* Redis (Distributed Limiting)
* Prometheus + Actuator
* Docker, Nginx

---

## ✍️ Author

**Manmath Jukale**

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

````
