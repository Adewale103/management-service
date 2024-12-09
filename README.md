# Management Service

**Base API URL:** `http://localhost:8080/api/v1/book`

## Features
- Create, retrieve, update, and delete books.
- Search books by criteria.

## API Endpoints
- `POST /api/v1/book`: Create a new book.
- `GET /api/v1/book`: Get a book by ID.
- `PUT /api/v1/book`: Update a book.
- `DELETE /api/v1/book`: Delete a book by ID.
- `GET /api/v1/book/all`: Retrieve all books.

## Swagger Documentation
- Visit `http://localhost:8080/swagger-ui.html` for API documentation.

## How to Build and Run

### 1. Clone the Repository
- git clone https://github.com/Adewale103/management-service.git
- cd management-service

### 2. Build the JAR
mvn clean package

### 3. Run the Service
java -jar target/management-service-0.0.1-SNAPSHOT.jar

### 4. Access the API
The service runs on http://localhost:8080. Example endpoints:

    GET /api/v1/book - Fetch all books.
    POST /api/v1/book - Add a new book.
    PUT /api/v1/book/{id} - Update a book.
    DELETE /api/v1/book/delete/{id} - Delete a book.

### 5. Configuration
Default database is H2. Update application.properties for another DB:

- spring.datasource.url=jdbc:mysql://<db_url>
- spring.datasource.username=<db_user>
- spring.datasource.password=<db_password>

