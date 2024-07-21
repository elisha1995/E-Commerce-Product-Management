# Project Documentation

## Project Overview

This project is an e-commerce application that includes both backend and frontend components. The backend is implemented in Java using Spring Boot, while the frontend is developed using React with TypeScript.

## Architecture Diagram

The architecture of this project is a standard multi-tier architecture with the following components:

- **Frontend:** React application interacting with the backend services via REST APIs.
- **Backend:** Spring Boot application exposing REST APIs, interacting with the database, and handling business logic.
- **Database:** A relational database storing application data.
- **Redis:** Used for caching and managing basket data.

  ![architectureDiagram](https://github.com/user-attachments/assets/37efb15e-7de5-4eb0-bc00-206bd703d597)

# Backend Components

## Package Structure

- **config:** Configuration classes for CORS, Security, and other application settings.
- **controller:** REST controllers handling HTTP requests and responses.
- **entity:** JPA entities representing the database tables.
- **exceptions:** Custom exception handlers and definitions.
- **health:** Health check indicators.
- **mapper:** Mapper classes for transforming data between different layers.
- **model:** Data Transfer Objects (DTOs) and response models.
- **repository:** Spring Data JPA repositories for database operations.
- **security:** Security-related classes such as filters and entry points.
- **service:** Service layer containing business logic and service implementations.

# Frontend Components

## Package Structure

- **api:** Contains files related to API interactions, such as agent.ts and basketService.ts.
- **errors:** Components for error handling, such as NotFoundError.tsx and ServerError.tsx.
- **layout:** Layout components including the main App.tsx, Header.tsx, and other UI elements like Spinner.tsx.
- **models:** TypeScript model definitions for various entities such as basket.ts, brand.ts, order.ts, product.ts, type.ts, and user.ts.
- **router:** Routing components including RequireAuth.tsx and Routes.tsx.
- **store:** Redux store configuration file configureStore.ts.
- **util:** Utility functions and helpers in util.ts.
- **features:**
  - **account:** Components and Redux slices related to user account management, such as accountSlice.ts, RegisterPage.tsx, and SignInPage.tsx.
  - **basket:** Components and Redux slices related to the shopping basket, such as BasketPage.tsx, basketSlice.ts, and BasketSummary.tsx.
  - **catalog:** Components for displaying and managing the product catalog, including Catalog.tsx, ProductCard.tsx, ProductDetails.tsx, and ProductList.tsx.
  - **checkout:** Components for the checkout process, such as AddressForm.tsx, CheckoutPage.tsx, PaymentForm.tsx, Review.tsx, and ValidationRules.ts.
  - **contact:** Contact page component ContactPage.tsx.
  - **home:** Home page component HomePage.tsx.
  - **orders:** Order-related components like Order.tsx.
 
# Key Components

- **App.tsx:** Main application component.
- **Header.tsx:** Header component for navigation and branding.
- **BasketPage.tsx:** Component for displaying the user's basket.
- **Catalog.tsx:** Component for displaying the product catalog.
- **ProductDetails.tsx:** Component for displaying detailed information about a product.
- **CheckoutPage.tsx:** Component for handling the checkout process.
- **SignInPage.tsx:** Component for user authentication.
- **RegisterPage.tsx:** Component for user registration.

# Getting Started

## Prerequisites

- **Java 21+**
- **Node.js 20+**
- **Docker**

# Docker Setup

1. **Ensure Docker is installed and running.**
2. **Navigate to the `docker` directory.**
3. **Start the services:**
   ```sh
   docker-compose up -d
   ```
# Running the Backend
1. Navigate to the backend directory.
2. Run the application using your IDE or execute:
  ```sh
  ./mvnw spring-boot:run
  ```
# Running the Frontend
1. Navigate to the frontend directory.
2. Install dependencies:
   ```sh
   npm install
   ```
3. Start the development server:
    ```sh
    npm start
    ```
