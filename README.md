
#  E-Commerce Web Application

A **full-stack e-commerce web application** built with **Angular** for the frontend and **Spring Boot** for the backend. Includes **MetaMask** integration for Web3 capabilities.

---

##  Technologies Used

### 🔹 Frontend – Angular
- Angular Material – Modern UI components  
- Bootstrap 5 – Responsive layout & styling  
- Tailwind CSS – Utility-first CSS  
- RxJS – Reactive programming  
- ngx-toastr – Toast notifications  
- Web3.js – Blockchain interactions  
- MetaMask Integration – Web3 wallet connection using `@metamask/detect-provider`  
- PostCSS – CSS transformation pipeline  

### 🔹 Backend – Spring Boot
- Spring Boot Starter Web – REST API development  
- Spring Data JPA – Database ORM  
- Spring Security – Secure endpoints  
- JWT (Java Web Tokens) – Authentication & Authorization  
- MySQL – Relational database  
- JUnit & Spring Boot Test – Backend testing  

### 🔹 Microservices Architecture
- Admin Service – Product, inventory, and admin dashboard  
- Authentication Service – Login, registration, JWT handling  
- User Service – Profile, address, user-specific data  
- Order Service – Orders, checkout, order history  
- Discovery Server – Service registration & discovery (Eureka)

---

##  Web3 Integration

MetaMask is integrated for decentralized login and wallet connectivity using:
- `@metamask/detect-provider`
- `web3.js`

---

##  Project Structure

```
ecom-frontend/         # Angular Frontend
ecom-backend/          # Monolithic Spring Boot backend (optional)
ecom-microservice/     # Microservices architecture
├── admin-service/     # Admin operations
├── auth-service/      # Authentication
├── user-service/      # User data management
├── order-service/     # Order processing
├── discovery-server/  # Eureka service registry
```

---

##  How to Run the Project

### 1. Clone the Repository
```
git clone https://github.com/mitigator/Clickshop.git
```

### 2. Run Frontend
```
cd ecom-frontend
npm install
npm start
```

### 3. Run Backend
Set your database credentials in the `application.properties` file.

Then:
```
cd ecom-backend
mvn clean install
cd target
java -jar ecommerce-0.0.1-SNAPSHOT.jar
```

Or, for microservices:
```
cd ecom-microservice/[service-name]
mvn clean install
cd target
java -jar [service-name].jar
```

---

## Database 

1. Create a Database
 ```bash
  CREATE DATABASE ecommerce_db;
  ```
2. Configure application.properties
```plaintext
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
3. No Manual Table Creation Required
With JPA's entity mapping and the hibernate.ddl-auto=update setting, tables will be automatically created based on your Java entity classes when the application starts up. No need to manually create tables or write SQL DDL scripts.



## Payment Integration with MetaMask

Follow the steps below to integrate payment functionality using MetaMask and Hardhat.

### 1. Install and Set Up Hardhat

- **Navigate to your MetaMask project folder:**
  ```bash
  cd metamask
  ```

- **Install Hardhat:**
  ```bash
  npm install --save-dev hardhat
  ```

- **Initialize Hardhat Project:**
  ```bash
  npx hardhat
  ```

- **Start a Local Hardhat Node:**
  ```bash
  npx hardhat node
  ```

- **View Wallet Accounts:**
  The wallet accounts will be displayed once the node is running.

---

### 2. Install MetaMask Extension

- **Download MetaMask for Chrome:**  
  [MetaMask Chrome Extension](https://chromewebstore.google.com/detail/metamask/nkbihfbeogaeaoehlefnkodbefgpgknn?hl=en)

---

### 3. Add Local Network to MetaMask

- **In MetaMask, select the network dropdown (default is “Ethereum Mainnet”):**  
  Make sure you have enabled “Show test networks” in MetaMask to view the full list.

- **Network Configuration:**
  - **Network Name:** Hardhat
  - **New RPC URL:** `http://127.0.0.1:8545/`
  - **Chain ID:** 31337
  - **Currency Symbol:** HardhatETH

---

### 4. Import Test Accounts into MetaMask

- **Go to the Accounts Tab in MetaMask (the colorful circle in the top right corner).**
- **Select the “Import Account” option.**
- **Paste the private keys from the Hardhat console** into the prompt that appears.

---

You should now have MetaMask configured for your local Hardhat network and be ready for payment integration in your application!


##  Contact

For questions or support, reach out via [shubhammishra4621@gmail.com] or raise an issue on the repository.
