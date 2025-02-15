# MoneyMap

## Overview

User Service is a microservice responsible for managing user-related operations, including authentication, user profile management, and user data storage. It is built using **Spring Boot** and follows a **microservices architecture**.

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL** (or any other relational database)
- **Maven** (for dependency management)
- **Docker** (optional, for containerization)

## Project Structure

```
user-service/
├── pom.xml                 # Project dependencies
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── moneymap/
│   │   │           └── userservice/
│   │   │               ├── config/         # Configuration classes
│   │   │               ├── controller/     # API endpoints
│   │   │               ├── model/          # Data models (entities)
│   │   │               ├── repository/     # Database access layer
│   │   │               ├── service/        # Business logic layer
│   │   │               └── exception/      # Exception handling
│   │   └── resources/
│   │       ├── application.yml  # Application configuration
│   │       └── data.sql         # Optional initial data
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── moneymap/
│       │           └── userservice/  # Unit and integration tests
└── README.md
```

## Getting Started

### Prerequisites

Ensure you have the following installed:

- [JDK 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/) (or another supported database)
- [Docker](https://www.docker.com/) (optional)

### Setup and Run

#### 1. Clone the Repository
```sh
git clone https://github.com/yourcompany/user-service.git
cd user-service
```

#### 2. Build the Project
```sh
mvn clean install
```

#### 3. Run the Application
```sh
mvn spring-boot:run
```

#### 4. API Endpoints

| Method | Endpoint        | Description            |
|--------|---------------|------------------------|
| GET    | `/users`       | Get all users         |
| POST   | `/users`       | Create a new user     |
| GET    | `/users/{id}`  | Get user by ID        |
| PUT    | `/users/{id}`  | Update user details   |
| DELETE | `/users/{id}`  | Delete user           |

#### 5. Running Tests
```sh
mvn test
```

## Docker Support

To build and run the service using **Docker**, use:

```sh
docker build -t user-service .
docker run -p 8080:8080 user-service
```

## Future Enhancements

- Implement JWT-based authentication.
- Add API documentation with Swagger.
- Introduce a caching mechanism for improved performance.

## License

This project is licensed under the **MIT License**.
