# Learning Management System (LMS) Backend

![Java](https://img.shields.io/badge/Java-17-red?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green?logo=spring)
![MongoDB](https://img.shields.io/badge/MongoDB-5.0-blue?logo=mongodb)
![License](https://img.shields.io/badge/License-MIT-blue)

A robust backend system for a Learning Management System built with Spring Boot and MongoDB, featuring role-based access control, JWT authentication, and comprehensive course management capabilities.

## Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)

## Features

### Role-Based Access
- **Instructors** can:
  - Create, update, and delete courses
  - View their created courses
- **Students** can:
  - Browse all available courses
  - Filter courses by category
  - Search Courses by keywords

### Security
- JWT-based authentication
- Secure password storage with BCrypt
- Role-based authorization using Spring Security

### API Features
- RESTful API design
- Comprehensive input validation
- Detailed error responses
- Swagger UI documentation

## Technology Stack

**Backend:**
- Java 17
- Spring Boot 3.0
- Spring Security
- Spring Data MongoDB

**Database:**
- MongoDB 5.0
- Document-based data modeling
- Index optimization

**Tools:**
- Maven
- Lombok
- MapStruct
- Swagger/OpenAPI 3.0
