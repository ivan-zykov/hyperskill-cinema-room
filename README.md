# Cinema Room REST Service (Kotlin + Spring Boot)

This project demonstrates a RESTful backend built with Kotlin and Spring Boot, focusing on maintainable architecture and testability.  
Provides REST endpoints for:
- listing all seats
- booking a seat
- returning a seat
- showing statistics secured by simple password  

## Tech Stack

- Kotlin
- Spring Boot
- Gradle

## Architecture

Partially layered design (Controller → Service + Repository). The service layer manages in-memory data to keep the implementation focused on API design and validation.

## Features

- RESTful API design
- Centralized exception handling
- DTOs are used to separate API contracts from internal models
- Partially covered with unit tests

## Requirements

- Java 21. Other versions not tested

## Run

### Linux/Mac

```bash
./gradlew bootRun
```

### Windows

```
gradlew.bat bootRun
```

## Default URL

http://localhost:28852

## API Overview

- GET /health — service health check
- GET /seats — retrieve all seats
- POST /purchase — book a seat
- POST /return — cancel seat reservation
- GET /stats — retrieve statistics (password protected)

## Future improvements

- Extend test coverage (controller and edge cases)
- Extend authentication for endpoints
- Replace in-memory storage with persistent database