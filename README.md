# Banking Services Application

This is a banking services application built with a microservices architecture. It provides basic banking functionalities such as client management, account management, and transactions.

## Architecture

The application is composed of three main microservices:

*   **`client-service`**: Manages all the information related to the clients.
*   **`account-service`**: Manages the bank accounts and transactions of the clients.
*   **`api-gateway`**: Acts as a single entry point for all the services.

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
*   **Logging**: SLF4J
*   **Mappers**: MapStruct
*   **Validation**: Jakarta Bean Validation for DTOs

## Database

The database used is PostgreSQL, it starts when the docker compose is executed for the first time, creating the databases for clients and accounts.

## API Endpoints

### API Gateway (`http://localhost:8080`)

The API Gateway is the single entry point for all the services. The endpoints are:

*   **Client Service:** `http://localhost:8080/api/v1/clients`
*   **Account Service:** `http://localhost:8080/api/v1/accounts`, `http://localhost:8080/api/v1/movements` and `http://localhost:8080/api/v1/reports`

### Client Service (`http://localhost:9080`)

*   **`POST /api/v1/clients`**: Creates a new client.
*   **`GET /api/v1/clients`**: Retrieves a list of all clients.
*   **`GET /api/v1/clients/{id}`**: Retrieves a client by their ID.
*   **`GET /api/v1/clients/clientId/{clientId}`**: Retrieves a client by their client ID.
*   **`PUT /api/v1/clients/{id}`**: Updates an existing client's information.
*   **`DELETE /api/v1/clients/{id}`**: Deletes a client by their ID.

### Account Service (`http://localhost:9090`)

#### Accounts
*   **`POST /api/v1/accounts`**: Creates a new bank account.
*   **`GET /api/v1/accounts/{accountNumber}`**: Retrieves a bank account by its account number.
*   **`PUT /api/v1/accounts/{accountNumber}`**: Updates an existing bank account.

#### Movements
*   **`POST /api/v1/movements`**: Creates a new bank transaction. When a movement is registered, the account balance is updated. If the balance is insufficient, an error with the message "Saldo no disponible" is returned.
*   **`GET /api/v1/movements/{id}`**: Gets bank transaction by movement id.
*   **`GET /api/v1/movements/account/{accountNumber}`**: Gets all bank transactions of an account by account number.

#### Reports
*   **`GET /api/v1/reports?clientId=<clientId>&startDate=<startDate>&endDate=<endDate>`**: Generates an account statement for a client within a specified date range. The report is returned in JSON format and includes the client's accounts with their balances and the details of the movements.

## How to Run

1.  **Build the services:**

    Before starting the application, you need to build the services. Navigate to the root directory of each service (`client-service`, `account-service`, and `api-gateway`) and run the following command:

    ```bash
    mvn clean package -DskipTests
    ```

2.  **Start the application:**

    Once the services are built, the entire application, including the microservices and the database, can be started using Docker Compose.

    ```bash
    docker-compose up -d --build
    ```

3.  **Verify the services:**

    *   **API Gateway:** `http://localhost:8080`
    *   **Client Service:** `http://client-service:9080`
    *   **Account Service:** `http://account-service:9090`

## API Documentation

API documentation for each service is available through Swagger UI at the API Gateway.

*   **API Gateway Documentation:** `http://localhost:8080/swagger-ui.html`

## Testing

The project includes unit and integration tests.

All tests can be run using the Maven `test` command in the root directory of each service.

### Postman Collection

A Postman collection is available in the `postman` directory to test the API endpoints. You can import the `postman_collection.json` file into Postman.