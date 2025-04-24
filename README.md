# E-Commerce Web Application

A full-stack e-commerce web application built with Angular for the frontend and Spring Boot for the backend. Includes MetaMask integration for Web3 capabilities.

---

## Technologies Used

### Frontend – Angular
- Angular Material – Modern UI components
- Bootstrap 5 – Responsive layout & styling
- Tailwind CSS – Utility-first CSS
- RxJS – Reactive programming
- ngx-toastr – Toast notifications
- Web3.js – Blockchain interactions
- MetaMask Integration – Detect provider for Web3 wallet connection
- PostCSS – CSS transformation pipeline
- JQuery – DOM manipulation

### Backend – Spring Boot
- Spring Boot Starter Web – REST API development
- Spring Data JPA – Database ORM
- Spring Security – Secure endpoints
- JWT (Java Web Tokens) – Authentication & Authorization
- MySQL – Relational database
- Stripe Java SDK – Payment integration
- JUnit & Spring Boot Test – Backend testing

### Microservices
- Admin Service
- Authentication Service
- User Service
- Order Service
- Discovery Server – Service registration and discovery for distributed architecture

---

## Web3 Integration
MetaMask is integrated for decentralized authentication and wallet connectivity using `@metamask/detect-provider` and `web3.js`.

---

## Project Structure (High-level)
```plaintext
ecom-frontend/         # Angular Frontend
ecom-backend/          # Spring Boot Backend
ecom-microservice/     # Microservices
├── admin-service/     # Admin microservice
├── auth-service/      # Authentication microservice
├── user-service/      # User-related operations
├── order-service/     # Order management
├── discovery-server/  # Service registry
Metamask
```plaintext

---
## How to Run the Project

1. **Clone the repository**
git clone https://github.com/your-username/your-repo-name.git

2. **Run Frontend**
cd ecom-frontend
npm install npm run dev

3. **Run Backend**
set database url in application.properties file
Open a new terminal:
cd ecom-backend
 mvn clean install
cd target
java -jar ecommerce-0.0.1-SNAPSHOT.jar

---

