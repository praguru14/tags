# Angular and Spring Boot App

This repository contains a basic Angular frontend application and a Spring Boot backend application.

## Prerequisites

Before running the application, ensure you have the following installed:

- Node.js and npm (for Angular)
- Java Development Kit (JDK) and Maven (for Spring Boot)

### Installing Node.js and npm

1. Update your package index:

    ```bash
    sudo apt update
    ```

2. Install Node.js and npm:

    ```bash
    sudo apt install nodejs npm
    ```

### Installing Java Development Kit (JDK) and Maven

1. Install OpenJDK:

    ```bash
    sudo apt install default-jdk
    ```

2. Install Maven:

    ```bash
    sudo apt install maven
    ```

## Installation

### Angular Frontend

1. Navigate to the `frontend` directory:

    ```bash
    cd frontend
    ```

2. Install dependencies:

    ```bash
    npm install
    ```

### Spring Boot Backend

1. Navigate to the `backend` directory:

    ```bash
    cd backend
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

## Running the Application

1. Start the Spring Boot backend server:

    ```bash
    java -jar backend/target/<name_of_your_jar_file>.jar
    ```

2. Start the Angular frontend:

    ```bash
    ng serve
    ```

3. Open your browser and navigate to `http://localhost:4200` to view the application.

## Additional Information

- The Angular frontend communicates with the Spring Boot backend through REST APIs.
- Default port for the backend is `8080` and for the frontend is `4200`.

## License

This project is licensed under the [MIT License](LICENSE).

## Support

For any questions or issues, please [open an issue](https://github.com/your-username/your-project-name/issues) or contact us directly.
