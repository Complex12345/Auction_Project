# Auction Platform

A full-stack auction platform built using **Spring Boot**, **React**, **TypeScript**, and **PostgreSQL**. This project allows users to register, log in using JWT authentication, browse auction listings, place bids, and manage their accounts.

## Features

### User Management

* User registration and login
* JWT-based authentication
* BCrypt password encryption
* Update username and password

### Auction System

* Browse active auctions
* View auction details
* Place bids on items
* Live countdown timer until auction expiration
* View the current highest bid

### Frontend

* React and TypeScript
* React Router navigation
* Axios for API communication

### Backend

* Spring Boot REST API
* Spring Security
* Spring Data JPA
* PostgreSQL
* Global exception handling

### Testing

* JUnit 5
* Mockito
* H2 in-memory database for tests

---

# Tech Stack

### Frontend

* React
* TypeScript
* Axios
* React Router

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT Authentication

### Database

* PostgreSQL
* H2 (Testing)

---

# Running the Project

From the project root, run:

```bash
docker compose up -d
```

To verify the containers are running:

```bash
docker ps
```

To stop the application:

```bash
docker compose down
```

---

## Application URLs

Frontend:

```
http://localhost:5173
```

Backend:

```
http://localhost:8080
```
