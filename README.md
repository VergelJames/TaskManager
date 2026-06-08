# TaskManager - RESTful Task Management API

A comprehensive Spring Boot application providing RESTful APIs for efficient task and user management. This application is designed to help users create, update, retrieve, and delete tasks while managing user profiles with role-based access control.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [API Documentation](#api-documentation)
  - [User Controller](#user-controller)
  - [Task Controller](#task-controller)
- [Authentication](#authentication)
- [Database](#database)
- [API Response Format](#api-response-format)
- [Error Handling](#error-handling)
- [Getting Started](#getting-started)
- [Contributing](#contributing)

## 📌 Overview

TaskManager is a robust RESTful API service built with Spring Boot that enables users to manage their tasks and profiles. The application supports multiple concurrent users with secure authentication mechanisms and provides paginated responses for better performance and scalability.

## ✨ Features

- **User Management**: Create, read, update, and delete user profiles
- **Task Management**: Full CRUD operations on tasks with pagination support
- **Authentication**: Secure endpoints with JWT token support (ready for integration)
- **Input Validation**: Comprehensive request validation using Jakarta validation annotations
- **Pagination Support**: Efficient data retrieval with pagination on list endpoints
- **Error Handling**: Centralized exception handling with meaningful error responses
- **In-Memory Database**: H2 database for development and testing
- **DTO Mapping**: Automatic entity-to-DTO conversion using MapStruct

## 🛠 Technology Stack

- **Java**: 17 (LTS)
- **Spring Boot**: 4.0.6
- **Database**: H2 (in-memory)
- **ORM**: JPA/Hibernate
- **Mapper**: MapStruct 1.6.3
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Maven
- **Additional Libraries**: Lombok (reducing boilerplate code)

## 📦 Prerequisites

Before running the application, ensure you have installed:

- Java Development Kit (JDK) 17 or higher
- Maven 3.6.0 or higher
- Git (for cloning the repository)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/VergelJames/TaskManager.git
cd TaskManager
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file directly:

```bash
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080` by default.

### 4. Access H2 Database Console

Navigate to `http://localhost:8080/h2-console` to view the in-memory database.

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

---

### **User Controller** (`/api/v1/users`)

Manages user profiles and user-related operations.

#### Endpoints:

##### 1. **Get User by UUID**
- **Endpoint**: `GET /users/{uuid}`
- **Description**: Retrieves a specific user by their UUID
- **Path Parameters**:
  - `uuid` (UUID): The unique identifier of the user
- **Response**: User details in UserDTO format
- **Status Code**: `200 OK`
- **Example**:
  ```bash
  curl -X GET "http://localhost:8080/api/v1/users/550e8400-e29b-41d4-a716-446655440000"
  ```

##### 2. **Get All Users**
- **Endpoint**: `GET /users`
- **Description**: Retrieves a paginated list of all users with optional filtering
- **Request Body**: UserParam (filter criteria)
- **Query Parameters**:
  - `page` (int): Page number (0-indexed)
  - `size` (int): Number of records per page
  - `sort` (string): Sort by field (e.g., `createdAt,desc`)
- **Response**: PaginatedResponse containing list of UserDTO objects
- **Status Code**: `200 OK`
- **Example**:
  ```bash
  curl -X GET "http://localhost:8080/api/v1/users?page=0&size=10&sort=createdAt,desc"
  ```

##### 3. **Create New User**
- **Endpoint**: `POST /users`
- **Description**: Creates a new user account
- **Request Body**: CreateUserRequest
  ```json
  {
    "firstName": "John",
    "middleName": "Michael",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": 1234567890
  }
  ```
- **Response**: Created UserDTO with UUID
- **Status Code**: `201 CREATED`
- **Validation**: 
  - Email must be valid and unique
  - Password must meet security requirements
  - First and last names are required
- **Example**:
  ```bash
  curl -X POST "http://localhost:8080/api/v1/users" \
    -H "Content-Type: application/json" \
    -d '{
    "firstName": "John",
    "middleName": "Michael",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": 1234567890
    }'
  ```

##### 4. **Update User**
- **Endpoint**: `PATCH /users/{userUuid}`
- **Description**: Partially updates an existing user (updates only provided fields)
- **Path Parameters**:
  - `userUuid` (UUID): The unique identifier of the user to update
- **Request Body**: UpdateUserRequest
  ```json
  {
    "firstName": "Jane",
    "email": "jane.doe@example.com"
  }
  ```
- **Response**: Updated UserDTO
- **Status Code**: `200 OK`
- **Note**: This is a partial update; only provided fields will be updated
- **Example**:
  ```bash
  curl -X PATCH "http://localhost:8080/api/v1/users/550e8400-e29b-41d4-a716-446655440000" \
    -H "Content-Type: application/json" \
    -d '{
      "firstName": "Jane",
      "email": "jane.doe@example.com"
    }'
  ```

##### 5. **Delete User**
- **Endpoint**: `DELETE /users/{userUuid}`
- **Description**: Permanently deletes a user account
- **Path Parameters**:
  - `userUuid` (UUID): The unique identifier of the user to delete
- **Response**: No content (empty body)
- **Status Code**: `204 NO CONTENT`
- **Warning**: This action is permanent and cannot be undone
- **Example**:
  ```bash
  curl -X DELETE "http://localhost:8080/api/v1/users/550e8400-e29b-41d4-a716-446655440000"
  ```

---

### **Task Controller** (`/api/v1/tasks`)

Manages tasks and task-related operations.

#### Endpoints:

##### 1. **Get All Tasks**
- **Endpoint**: `GET /tasks`
- **Description**: Retrieves a paginated list of all tasks with optional filtering
- **Request Body**: TaskParam (filter criteria - e.g., status, priority, owner)
- **Query Parameters**:
  - `page` (int): Page number (0-indexed)
  - `size` (int): Number of records per page
  - `sort` (string): Sort by field (e.g., `createdAt,desc`)
- **Response**: PaginatedResponse containing list of TaskDTO objects
- **Status Code**: `200 OK`
- **Example**:
  ```bash
  curl -X GET "http://localhost:8080/api/v1/tasks?page=0&size=20&sort=dueDate,asc"
  ```

##### 2. **Get Task by UUID**
- **Endpoint**: `GET /tasks/{taskUuid}`
- **Description**: Retrieves a specific task by its UUID
- **Path Parameters**:
  - `taskUuid` (UUID): The unique identifier of the task
- **Response**: Task details in TaskDTO format
- **Status Code**: `200 OK`
- **Example**:
  ```bash
  curl -X GET "http://localhost:8080/api/v1/tasks/550e8400-e29b-41d4-a716-446655440001"
  ```

##### 3. **Create New Task**
- **Endpoint**: `POST /tasks`
- **Description**: Creates a new task
- **Request Body**: CreateTaskRequest
  ```json
  {
    "title": "Complete project documentation",
    "description": "Write comprehensive documentation for the API",
    "status": "INP",
    "priority": "HI",
    "ownerUuid": "550e8400-e29b-41d4-a716-446655440000",
    "dueDate": "2024-12-31"
  }
  ```
- **Response**: Created TaskDTO with UUID
- **Status Code**: `201 CREATED`
- **Validation**:
  - Title is required and must not be empty
  - Owner UUID must be a valid existing user
  - Due date must be in valid format
- **Example**:
  ```bash
  curl -X POST "http://localhost:8080/api/v1/tasks" \
    -H "Content-Type: application/json" \
    -d '{
      "title": "Complete project documentation",
      "description": "Write comprehensive documentation for the API",
      "status": "INP",
      "priority": "HI",
      "ownerUuid": "550e8400-e29b-41d4-a716-446655440000",
      "dueDate": "2024-12-31"
    }'
  ```

##### 4. **Update Task**
- **Endpoint**: `PATCH /tasks/{taskUuid}`
- **Description**: Partially updates an existing task (updates only provided fields)
- **Path Parameters**:
  - `taskUuid` (UUID): The unique identifier of the task to update
- **Request Body**: UpdateTaskRequest
  ```json
  {
    "status": "COM"
  }
  ```
- **Response**: Updated TaskDTO
- **Status Code**: `200 OK`
- **Note**: This is a partial update; only provided fields will be updated
- **Example**:
  ```bash
  curl -X PATCH "http://localhost:8080/api/v1/tasks/550e8400-e29b-41d4-a716-446655440001" \
    -H "Content-Type: application/json" \
    -d '{
      "status": "COM"
    }'
  ```

##### 5. **Delete Task**
- **Endpoint**: `DELETE /tasks/{taskUuid}`
- **Description**: Permanently deletes a task
- **Path Parameters**:
  - `taskUuid` (UUID): The unique identifier of the task to delete
- **Response**: No content (empty body)
- **Status Code**: `204 NO CONTENT`
- **Warning**: This action is permanent and cannot be undone
- **Example**:
  ```bash
  curl -X DELETE "http://localhost:8080/api/v1/tasks/550e8400-e29b-41d4-a716-446655440001"
  ```

---

## 🔐 Authentication

The application is configured to support **JWT (JSON Web Token) authentication** combined with **Basic Authentication** for secure API access.

### Current Status
- Spring Security starter is commented out in the pom.xml but can be enabled for production use
- All endpoints are currently open but ready for security enforcement

### Future Implementation

When security is enabled:

1. **Basic Authentication** for initial login to obtain JWT token
2. **JWT Token** for subsequent API requests
3. **Role-Based Access Control (RBAC)** for endpoint authorization

### Usage with Authentication (Once Enabled)

```bash
# Step 1: Obtain JWT token via Basic Auth
curl -X POST "http://localhost:8080/auth/login" \
  -H "Authorization: Basic base64(username:password)"

# Step 2: Use token in subsequent requests
curl -X GET "http://localhost:8080/api/v1/tasks" \
  -H "Authorization: Bearer <your-jwt-token>"
```

---

## 💾 Database

The application uses **H2 in-memory database** for development and testing purposes.

### Database Console
- **URL**: `http://localhost:8080/h2-console`
- **Driver Class**: `org.h2.Driver`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave blank)

### Data Initialization

Initial data is loaded from `data.sql` on application startup:

```sql
-- Users table with sample data
-- Tasks table with sample data
```

To view initial data, check the file: `src/main/resources/data.sql`

---

## 📋 API Response Format

### Success Response Format

```json
{
  "uuid": "550e8400-e29b-41d4-a716-446655440000",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-15T10:30:00Z"
}
```

### Paginated Response Format

```json
{
  "content": [
    { /* object 1 */ },
    { /* object 2 */ }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "totalElements": 45,
    "totalPages": 5
  },
  "hasNext": true,
  "hasPrevious": false
}
```

---

## ⚠️ Error Handling

The application includes comprehensive error handling with meaningful error messages.

### Common Error Responses

| Status Code | Scenario | Example Response |
|-------------|----------|------------------|
| **400** | Bad Request / Validation Error | `{ "message": "Invalid email format", "errors": [...] }` |
| **404** | Resource Not Found | `{ "message": "User not found with UUID: xxx" }` |
| **409** | Conflict (e.g., duplicate email) | `{ "message": "Email already exists" }` |
| **500** | Internal Server Error | `{ "message": "An unexpected error occurred" }` |

### Error Response Structure

```json
{
  "id": "err-550e8400-e29b-41d4-a716-446655440000",
  "errorCode": "VALIDATION_FAILED",
  "message": "Validation failed",
  "status": 400,
  "reason": "Bad Request",
  "timestamp": "2024-01-15T10:30:00Z",
  "details": {
    "email": "Email must be valid",
    "firstName": "First name is required"
  },
  "path": "/api/v1/users"
}
```

---

## 🏃 Getting Started

### Quick Start Example

```bash
# 1. Start the application
mvn spring-boot:run

# 2. Create a user
curl -X POST "http://localhost:8080/api/v1/users" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
  }'

# 3. Create a task (replace UUID with the user UUID from step 2)
curl -X POST "http://localhost:8080/api/v1/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Task",
    "description": "This is my first task",
    "status": "INP",
    "priority": "HI",
    "ownerUuid": "<user-uuid-from-step-2>"
  }'

# 4. Retrieve all tasks
curl -X GET "http://localhost:8080/api/v1/tasks?page=0&size=10"

# 5. Update the task status
curl -X PATCH "http://localhost:8080/api/v1/tasks/<task-uuid>" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }'
```
---

## 📧 Contact & Support

- **Author**: James Viray
- **Email**: vergeljamesviray@gmail.com
- **GitHub**: [VergelJames/TaskManager](https://github.com/VergelJames/TaskManager)

---

## 🔄 Future Enhancements

- [ ] Enable and integrate Spring Security with JWT
- [ ] Implement role-based access control (RBAC)
- [ ] Add task assignment to multiple users
- [ ] Add Room feature to group tasks
- [ ] Add Group feature to group users
- [ ] Implement task comments and activity tracking
- [ ] Add email notifications
- [ ] Implement task categories/tags
- [ ] Add advanced filtering and search capabilities
- [ ] Deploy to cloud platform (Azure, AWS, GCP)
- [ ] Implement WebSocket for real-time updates
- [ ] Add comprehensive unit and integration tests