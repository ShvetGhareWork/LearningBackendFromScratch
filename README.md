A structured implementation of a highly scalable, event-driven microservices architecture.

This repository documents the progression from foundational REST API development to a distributed systems model. It demonstrates the decoupling of domain logic (like user authentication) from asynchronous processes (like notification delivery) using an internal event bus.

🏗️ Architecture & Tech Stack
This project utilizes a modern Java ecosystem and self-managed message brokers to ensure high throughput and data integrity.

Core Framework: Java 17+ / Spring Boot 3.x

Data Persistence: PostgreSQL via Spring Data JPA / Hibernate

Security: Spring Security (BCrypt Password Encoding, Stateless API design)

Event Streaming: Apache Kafka (Configured in KRaft mode, eliminating ZooKeeper dependencies)

Infrastructure: Docker & Docker Compose (via Spring Boot Docker Compose integration)

Boilerplate Reduction: Lombok

⚙️ System Components
Currently, the system comprises the following logical layers:

Auth Service: Handles user registration and authentication. Persists user state securely to PostgreSQL.

Event Publisher: A Kafka Producer integrated within the transactional boundaries of the Auth Service. Emits highly structured JSON payloads (e.g., SIGNUP_SUCCESS) to specific topics upon successful state changes.

Notification Consumer (WIP): A decoupled service that listens to the registration topics to handle external API integrations (e.g., triggering email delivery) without blocking the primary request thread.

🚀 Local Development Setup
Prerequisites
JDK 17 or higher

Maven 3.8+

Docker Desktop / Docker Engine (Required for local infrastructure)

1. Infrastructure Provisioning
The project relies on Spring Boot's native Docker Compose support. Upon application startup, Spring Boot will automatically pull and provision the necessary PostgreSQL and Kafka (KRaft) containers defined in the compose.yml.

2. Configuration
Ensure your application.properties or application.yml is configured for the local environment.

Properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/backend_db
spring.datasource.username=postgres
spring.datasource.password=your_secure_password
spring.jpa.hibernate.ddl-auto=update

# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.acks=all
3. Execution
Compile and boot the application via Maven:

Bash
mvn clean install
mvn spring-boot:run
🧪 Testing the API
You can verify the event-driven registration flow using the following cURL command (or via Postman/Insomnia):

Bash
curl -X POST http://localhost:8080/api/auth/signup \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Alice Smith",
           "email": "alice.smith@example.com",
           "password": "securepassword123"
         }'
To verify the Kafka integration, monitor your broker logs or attach a CLI consumer to the user-registration-events topic to observe the emitted JSON payloads.

🗺️ Roadmap / Backlog
[x] Establish base Spring Boot project and JPA configurations.

[x] Implement secure User entity mapping and PostgreSQL integration.

[x] Configure Auth Controller and BCrypt password hashing.

[x] Provision Kafka KRaft cluster via Docker Compose.

[x] Implement Kafka Producer for asynchronous registration events.

[ ] Implement Kafka Consumer for email delivery integration.

[ ] Transition to JWT-based stateless session management.

[ ] Introduce global exception handling and standardized API error responses.
