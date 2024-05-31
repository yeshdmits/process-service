# Process-Service
***

## Build & Run the Application Locally 
***
### 1. Build the Project
- First, clean and build the project using Maven:
```shell
mvn clean install
```
***
### 2. Start Keycloak and Database Containers
- Next, start the auth and database containers using Docker Compose:
```shell
docker-compose up -d KEYCLOAK DB
```
***
### 3. Configure OAuth2 Flow
- To enable proper OAuth2 flow, you need to modify your `/etc/hosts` file by adding the IP address of the Keycloak Docker container.
#### Retrieve the Docker Container IP Address
- Run the following command to get the IP address of the Keycloak container:
```shell
docker container inspect process-service-keycloak | grep IPAddress
```
#### Expected Output
- The command should return an output similar to:
``` json
"IPAddress": "<IP>"
```
- Replace `<IP>` with the actual IP address from the output.
#### Update the Hosts File
- Run the following command to update your `/etc/hosts` file with the retrieved IP address:
```shell
sudo sed -i '2a <IP> keycloak' /etc/hosts
```
- Make sure to replace `<IP>` with the actual IP address obtained from the previous step.
***
### 4. Start the Remaining Containers:
- Finally, start the rest of the containers (API and UI) using Docker Compose:
```shell
docker-compose up -d API UI
```
***
### 5. Access the Application
- Open your web browser and connect to http://localhost.

***
***
***
***
## Fullstack Development Setup
### 1. Build the Project
- First, clean and build the projects using Maven:
```shell
mvn clean install -f process-service-ui/pom.xml
```
```shell
mvn clean install -f process-service-api/pom.xml
```
***
### 2. Start Keycloak and Database Containers
- Next, start the auth and database containers using Docker Compose:
```shell
docker-compose up -d KEYCLOAK DB
```
***
### 3. Run Frontend
- Next, start the Frontend Application:
```shell
mvn spring-boot:run -f process-service-ui/pom.xml
```
### 3. Run Backend
- Next, start the Keycloak and database containers using Docker Compose:
```shell
mvn spring-boot:run -f process-service-api/pom.xml
```
***
### 4. Access the Application
- Open your web browser and connect to http://localhost:8060.