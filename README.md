# E-Commerce Inventory & Order Microservices

This project demonstrates a simple microservices-based e-commerce backend using Spring Boot 4, covering inventory management, order placement, inter-service communication, database migrations, and testing.

## Tech Stack

Java 21

Spring Boot 4.0.0

Spring Web (MVC)

Spring Data JPA

H2 In-Memory Database

Liquibase (DB migrations)

Mockito + JUnit 5 (Unit tests)

Rest Assured (API integration tests)

Maven

## Services Overview

### 1) Inventory Service

Responsible for:

Maintaining inventory batches per product

Deducting inventory based on expiry (FIFO by expiry date)

Returning available inventory details

Runs on port 8081

### 2) Order Service

Responsible for:

Placing orders

Reserving inventory via Inventory Service

Persisting order details

Runs on port 8082

## Project Setup Instructions

### Prerequisites

Java 21 installed

Maven 3.9+

IDE (IntelliJ / VS Code recommended)

### Run Inventory Service

cd inventory-service
mvn clean spring-boot:run

Service URL: http://localhost:8081

H2 Console: http://localhost:8081/h2-console

### Run Order Service

cd order-service
mvn clean spring-boot:run

Service URL: http://localhost:8082

H2 Console: http://localhost:8082/h2-console

## Database

Database is auto-created on startup

Liquibase executes:

Table creation

Sample data loading (CSV/XML)

No manual DB setup required.

## API Documentation

Inventory APIs

#### GET Inventory by Product

- GET /inventory/{productId}

**Response**

{
"productId": 1001,
"productName": "Laptop",
"batches": [
{
"batchId": 1,
"quantity": 50,
"expiryDate": "2025-12-31"
},
{
"batchId": 2,
"quantity": 30,
"expiryDate": "2026-03-15"
}
]
}

#### POST Update Inventory (Reservation)

- POST /inventory/update

Request

{
"productId": 1002,
"quantity": 3
}

Response

[3]

Returns list of batchIds from which inventory was reserved.

**Order APIs**

POST Place Order

- POST /order

Request

{
"productId": 1002,
"quantity": 3
}

Response

{
"orderId": 5012,
"productId": 1002,
"productName": "Smartphone",
"quantity": 3,
"status": "PLACED",
"reservedBatchIds": [3],
"message": "Order placed. Inventory reserved."
}

### Inventory ↔ Order Communication

Order Service receives order request
Calls Inventory Service **/inventory/update**

Inventory Service:

- Deducts stock using expiry-based strategy

- Returns reserved batch IDs

- Order is saved with PLACED status

## Testing Instructions

### Unit Tests (JUnit 5 + Mockito)

Covers:

- Service logic

- Handler logic

- Controller delegation

Run:

mvn test

Examples:

- InventoryServiceImplTest

- ExpiryBasedInventoryHandlerTest

- OrderServiceImplTest

- InventoryControllerTest

- OrderControllerTest

### Integration Tests (Rest Assured + H2)

Starts application on random port

Uses in-memory H2 DB

Hits real REST endpoints

Run:

mvn test

Examples:

- InventoryServiceIntegrationTest

### Author - **Ayush Sharma**
