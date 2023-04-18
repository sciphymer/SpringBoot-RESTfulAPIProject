# Critter Chronologer Project

This is a Software as a Service (Saa) application project that provides a backend application with API interface for a small business that takes care of animals. This Spring Boot project will allow users to create pets, owners, and employees, and then schedule events for employees to provide services for pets.

### The Project Starter Code

### The Back-End

This project using the following technologies for the backend service:
1. The Spring Boot application connect the external database MySQL database for data persistenace.
2. Data Transfer Object (DTO) pattern for transfering request back and forth the backend controller layer to the service layer.
3. Data Layer of the backend is access through the Spring Data JPA.
4. Data models are written in Java Entities. CRUD operation are supported by mapping the entity through the JPA repository using JPQL query.
4. H2 in-memory database is used for running test cases.


