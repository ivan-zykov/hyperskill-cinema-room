# Cinema Room REST Service (Kotlin + Spring Boot)

REST API for basic bookkeeping of a cinema.  
Supports listing all seats, booking and returning a seat, showing statistics secured by simple password  

## Stack

Kotlin · Spring Boot · Gradle

## Architecture

Partially layered design (Controller → Service + Repository). For simplicity service handles in-memory data

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

## Notes

- Tests of the controller are missing
- Tests of the service don't cover the functionality of listing, purchasing and returning tickets
