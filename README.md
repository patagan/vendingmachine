# Vending Machine App

This is a simple vending machine app built with Vue.js and Spring Boot.

## Requirements

- Node.js v18.x or higher
- JDK 17 or higher
- MySQL Server

## Configuration

Configure the database connection in `backend/src/main/resources/application.yml`

## Run

To build the project, run the following command from the root directory:

   docker-compose up

Access the frontend application at `http://localhost:3000`

Access the backend application at `http://localhost:8080`

Access the database application at `http://localhost:3306`

## Important

If you are starting the 3 tier application with docker-compose, you may need to restart it again once the Database is created. 
Otherwise the backend will not be able to connect.