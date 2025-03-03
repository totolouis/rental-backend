TODO:
- [x] var env pour les secrets (app properties & jwt token dans security)
- [x] Global exception handler pour gerer les exceptions (comme ca on renvoit automatiquement les exception depuis les controllers avec les ResponseEntity) -  DONE. Need to add more exceptions on the way
- [ ] Utiliser les mappers pour transformer Entity en DTO et inversement - in progress
- [ ] Faire renvoyer les DTO directement depuis les controllers et ainsi Migrer la logique des controllers dans les services
- [ ] Config swagger pour piuvoir mettre le token jwt et ainsi faire des query protected. (file openapi.cs)
  - [ ] Faire un fichier de config Swagger
  - [ ] https://www.baeldung.com/spring-boot-swagger-jwt
  - [ ] 

# Rental Project

This project is part of the Formation OpenClassroom Fullstack course. It focuses on building a backend for a rental service.

## Table of Contents

- [Rental Project](#rental-project)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Features](#features)
  - [Installation](#installation)
    - [Prepare the database](#prepare-the-database)
  - [Usage](#usage)
  - [Usage with the Frontend from OpenClassroom](#usage-with-the-frontend-from-openclassroom)
  - [Swagger](#swagger)

## Introduction

The Rental Project is a backend service designed to manage rental properties. It provides an API for interacting with the rental data.

## Features

- User authentication and authorization
- CRUD operations for properties, tenants, and leases
- Search and filter capabilities
- API documentation with Swagger

## Installation

To install and run the project locally, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/rental.git
    ```
2. Navigate to the project directory:
    ```bash
    cd rental
    ```
3. Install dependencies:
    ```bash
    mvn install
    ```
4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

On Macosx, use:
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DOC_MYSQL_USER='' -DOC_MYSQL_PWD='' -DOC_JWT_KEY=''"`

Access swagger here: `http://[LINK]/swagger-ui/index.html`

### Prepare the database

A mysql db should run at localhost:3307.
Init a database called `openclassroom` with username `root` and password `root` and execute the following script to create the db:

```sql
CREATE TABLE `users` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `rentals` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `messages` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE UNIQUE INDEX `USERS_index` ON `users` (`email`);

ALTER TABLE `rentals` ADD FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`user_id`) REFERENCES `USusersERS` (`id`);

ALTER TABLE `messages` ADD FOREIGN KEY (`rental_id`) REFERENCES `rentals` (`id`);

```

If you already have a db set up, you can change the db settings at your own convenience at `ressources/application.properties` if needed.

## Usage

Once the application is running, you can access the API at `http://localhost:3001`. Use tools like Postman or cURL to interact with the endpoints.

## Usage with the Frontend from OpenClassroom

In order to save correctly the image and see them, you can put the frontend project in a folder called `frontend` just outside the backend. Something like that:

```
./sources
   backend/
       src/
       ...
   frontend/
       src/
       ...
```

## Swagger

You can access the swagger here: http://localhost:3002/swagger-ui/index.html.