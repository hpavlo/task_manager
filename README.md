# Task Management API

This application provides a RESTful API for managing tasks. It allows users to create, retrieve, update, and delete tasks.

## Table of Contents

-   [Features](#features)
-   [Getting Started](#getting-started)
    -   [Prerequisites](#prerequisites)
    -   [Installation](#installation)
    -   [Running the Application](#running-the-application)
-   [API Endpoints](#api-endpoints)
-   [Scheduled Tasks](#scheduled-tasks)
-   [Error Handling](#error-handling)
-   [Testing](#testing)
-   [Logging](#logging)
-   [Contributing](#contributing)

## Features

-   Create, retrieve, update, and delete tasks.
-   Update task status.
-   Retrieve task execution time.
-   Automatic pausing of tasks at midnight.
-   Input validation.
-   Error handling for task not found and validation errors.
-   Unit and integration tests.
-   Logging with Log4j.

## Getting Started

### Prerequisites

-   Java 17
-   Maven

### Installation

1.  Clone the repository:

    ```bash
    git clone git@github.com:hpavlo/task_manager.git
    ```

2.  Navigate to the project directory:

    ```bash
    cd task_manager/TaskManager.API
    ```

3.  Build the application using Maven or Gradle:

    ```bash
    mvn clean install
    ```

4.  Configure the database connection in `application.properties`.

### Running the Application

1.  Run the application using Maven or Gradle:

    **Maven:**

    ```bash
    mvn spring-boot:run
    ```

    **Gradle:**

    ```bash
    gradle bootRun
    ```

2.  The application will be accessible at `http://localhost:8080`.

## API Endpoints

-   `GET /tasks`: Retrieve all tasks.
-   `GET /tasks/{id}`: Retrieve a task by ID.
-   `POST /tasks`: Create a new task.
-   `PATCH /tasks/{id}`: Update a task.
-   `PUT /tasks/{id}?status={status}`: Update task status.
-   `DELETE /tasks/{id}`: Delete a task.

## Scheduled Tasks

-   Tasks are automatically paused at midnight (00:00:00) using a scheduled task.

## Error Handling

-   `404 Not Found`: Returned when a task is not found.
-   `400 Bad Request`: Returned for validation errors.
-   Detailed error messages are included in the response body.

## Testing

-   Unit and integration tests are included in the `src/test/java` directory.
-   Run tests using Maven:

    ```bash
    mvn test
    ```

## Logging

-   Logging is implemented using Log4j.
-   Configuration is in `src/main/resources/log4j2.xml`.

## Contributing

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Commit your changes.
4.  Push to the branch.
5.  Create a pull request.
