
# REST-Services-AuthToolkit

- 1. BasicAuth (REST Services using Basic-Authentication) 
- 2. JwtAuth (REST Services using JWT Authentication)

# BasicAuth 
This is a Spring Boot-based REST service that demonstrates basic authentication and security configurations. The project includes a simple controller, security setup, and utility classes to handle authentication and request processing.

# JwtAuth
JwtAuth is a Spring Boot-based RESTful authentication toolkit that provides JWT-based authentication and authorization services. It includes features for managing customers, creating, updating, and deleting customer data, and securing endpoints using Spring Security.

## Features

- **Spring Security Integration**: Configured with `SecurityFilterChain` for modern Spring Security practices.
- **Basic Authentication**: Implements Basic Authentication.
- **JWT Authentication**: Implements JWT Authentication
- **RESTful APIs**: Features rest services. sample services for managing customer data.
- **Configurable**: Configurable properties via `application.yml`.
- **REST Endpoints**: Provides a sample endpoint for submitting data.
- **JUnit 5 and Mockito Tests**: Includes unit tests for the application and controller.
- **Postman Collection's**: A Postman collection is provided for both Auth REST services, with each service in its own folder.

## Prerequisites

- **Java**: JDK 22 
- **Maven**: 3.8.0 or higher
- **Spring Boot**: 3.x
- **Postman**: For testing REST endpoints (optional)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/vivan-vj/REST-ServicesAuthToolkit.git
cd REST-ServicesAuthToolkit/Java/BasicAuth
cd REST-ServicesAuthToolkit/Java/JwtAuth

## Build the Project

- **mvn clean install**

## Run the Application

For BasicAuth REST Service
- **java -jar target/BasicAuthRestService.jar** 

For JwtAuth REST service
- **java -jar target/JwtAuthRestService.jar** 


