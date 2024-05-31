# Process-Service-API
***
## Setup Backend Development Environment
***
### 1. Start DB Container
- Next, start the database container using Docker Compose:
```shell
docker-compose up -d DB
```
***
### 2. Run Spring Boot Application
- Next, start the Keycloak and database containers using Docker Compose:
```shell
mvn spring-boot:run
```
***
### 3. Access the Application
- Open your web browser and connect to http://localhost:8081.