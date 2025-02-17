# Elasticsearch Demo Java

This project is a Spring Boot application demonstrating integration with Elasticsearch. It provides a RESTful API for managing product documents in Elasticsearch.

## Features

- Create, read, update, and delete product documents in Elasticsearch
- Search products by name
- Delete entire product indices

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle
- Elasticsearch 8.x

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run `./gradlew bootRun`

The application will start on `http://localhost:8080`

## API Documentation

The API documentation is available via Swagger UI. Once the application is running, you can access it at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

- POST /api/products - Create a new product
- GET /api/products/{id} - Get a product by ID
- GET /api/products/search - Search for products
- DELETE /api/products/{id} - Delete a product
- DELETE /api/products/index - Delete an entire product index

For detailed information about request/response formats, please refer to the Swagger documentation.

## Technologies Used

- Spring Boot
- Elasticsearch Java Client
- Lombok
- SpringDoc OpenAPI