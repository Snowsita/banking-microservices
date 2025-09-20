# Banking Services Application

This is a banking services application built with a microservices architecture. It provides basic banking functionalities such as client management, account management, and transactions.

## Architecture

The application is composed of two main microservices:

*   **`client-service`**: Manages all the information related to the clients.
*   **`account-service`**: Manages the bank accounts and transactions of the clients.

Each service is a Spring Boot application with its own PostgreSQL database.

### Considerations on Performance, Scalability, and Resilience

*   **Performance:** The services are lightweight and use a reactive web client for non-blocking communication, which contributes to better performance.
*   **Scalability:** The microservices architecture allows for independent scaling of each service based on demand.
*   **Resilience:** The use of containers and an orchestrator like Docker Compose provides a certain level of resilience. For a production environment, a more robust solution like Kubernetes with health checks and automatic restarts is recommended.

## Technologies

*   **Backend**: Spring Boot, Java 17
*   **Database**: PostgreSQL
*   **Build Tool**: Maven
*   **API Documentation**: SpringDoc OpenAPI
*   **Containerization**: Docker

## Database

The database used is PostgreSQL, it starts when the docker compose is executed for the first time, creating the databases for clients and accounts.

## API Endpoints

### Client Service (`http://localhost:9080`)

*   **`POST /api/v1/clientes`**: Creates a new client.
*   **`GET /api/v1/clientes`**: Retrieves a list of all clients.
*   **`GET /api/v1/clientes/{id}`**: Retrieves a client by their ID.
*   **`PUT /api/v1/clientes/{id}`**: Updates an existing client's information.
*   **`DELETE /api/v1/clientes/{id}`**: Deletes a client by their ID.

### Account Service (`http://localhost:9090`)

*   **`POST /api/v1/cuentas`**: Creates a new bank account.
*   **`POST /api/v1/movimientos`**: Creates a new bank transaction. When a movement is registered, the account balance is updated. If the balance is insufficient, an error with the message "Saldo no disponible" is returned.
*   **`GET /reports?fecha=<startDate>to<endDate>&cliente=<clientId>`**: Generates an account statement for a client within a specified date range. The report is returned in JSON format and includes the client's accounts with their balances and the details of the movements.

## How to Run

1.  **Start the application:**

    The entire application, including the microservices and the database, can be started using Docker Compose.

    ```bash
    docker-compose up -d
    ```

2.  **Verify the services:**

    *   **Client Service:** `http://localhost:9080`
    *   **Account Service:** `http://localhost:9090`

## API Documentation

API documentation for each service is available through Swagger UI.

*   **Client Service API:** `http://localhost:9080/swagger-ui.html`
*   **Account Service API:** `http://localhost:9090/swagger-ui.html`

## Testing

The project includes unit and integration tests.

*   **Unit Tests:** There is a unit test for the `Cliente` entity in the `client-service`.
*   **Integration Tests:** There is an integration test for the `ClienteController` in the `client-service`.

All tests can be run using the Maven `test` command in the root directory of each service.
