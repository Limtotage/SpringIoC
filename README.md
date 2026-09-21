[🇹🇷 Türkçe README](README_TR.md)
# 🛒 E-Commerce Website

A full-stack e-commerce application developed using **Spring Boot** and **Angular**. The project includes essential e-commerce features such as user management, product and category management, shopping cart operations, stock control, and role-based authorization.

## 🚀 Technologies

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT Authentication
* Hibernate
* Maven
* PostgreSQL
* Docker

### Frontend

* Angular
* TypeScript
* HTML
* SCSS
* npm

## 📁 Project Structure

```text
E-Commerce/
│
├── E-Ticaret-Backend/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── ...
│
└── E-Ticaret-Frontend/
    ├── src/
    ├── package.json
    └── ...
```

## ⚙️ Requirements

Before running the project, make sure the following tools are installed:

* Java
* Node.js
* npm
* Docker
* PostgreSQL / PostgreSQL through Docker

You can check the installed versions with:

```bash
java -version
node -v
npm -v
docker --version
```

---

# ▶️ Running the Project

The project consists of two main parts:

* **Backend:** Spring Boot
* **Frontend:** Angular

The backend and frontend should be run in **separate terminals**.

## 1. Start the Backend

First, navigate to the backend directory:

```bash
cd E-Ticaret-Backend
```

Then start the Spring Boot application using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Once the backend starts successfully, the Spring Boot application will run by default at:

```text
http://localhost:8080
```

> On Linux/macOS, if `./mvnw` does not have execution permission, run:

```bash
chmod +x mvnw
```

Then start the application again:

```bash
./mvnw spring-boot:run
```

---

## 2. Start the Frontend

While the backend is running, open a **new terminal** and navigate to the frontend directory:

```bash
cd E-Ticaret-Frontend
```

If the npm dependencies have not been installed yet:

```bash
npm install
```

Then start the Angular application:

```bash
npm start
```

The frontend will usually be available at:

```text
http://localhost:4200
```

---

# 🔐 User Roles

The application uses role-based authorization.

The main roles are:

* **ADMIN** — Management and administrative operations
* **CUSTOMER** — Customer-related operations
* **SELLER** — Seller-related operations

Access to certain operations depends on the user's assigned role.

## 🛍️ Main Features

### User Management

* User registration and login
* JWT-based authentication
* Role-based authorization
* Admin, Customer, and Seller roles

### Product Management

* Add products
* List products
* Update products
* Delete products
* Stock management
* Category-based product operations

### Category Management

* Create categories
* List categories
* Category approval operations
* Provide approved categories to sellers

### Shopping Cart

* Add products to the cart
* Remove products from the cart
* Manage cart items
* Stock control

### Security

* Spring Security
* JWT Authentication
* Role-based authorization
* Protection against unauthorized endpoint access

---

# 🗄️ Database

The project uses **PostgreSQL** as its database.

PostgreSQL can be run through Docker using the project's Docker configuration.

Example database connection settings:

```text
Host: localhost
Port: 5432
Database: StoreDB
Username: myuser
Password: secret
```

You can use **DBeaver** or another PostgreSQL database client to inspect and manage the database.

---

# 🔄 Startup Order

The recommended startup order is:

```text
1. Docker / PostgreSQL
        ↓
2. E-Ticaret-Backend
        ↓
3. ./mvnw spring-boot:run
        ↓
4. Open a new terminal
        ↓
5. Frontend directory
        ↓
6. npm start
        ↓
7. http://localhost:4200
```

The backend should be running before using the frontend so that API requests can be processed correctly.

---

# 🛠️ Development

### Backend

```bash
cd E-Ticaret-Backend
./mvnw spring-boot:run
```

### Frontend

Open a new terminal:

```bash
cd E-Ticaret-Frontend
npm start
```

Angular automatically detects frontend changes during development and recompiles the application.

---

## 📌 Note

This project is a full-stack e-commerce application developed for educational and development purposes. The backend and frontend are separate applications and must be run independently.

**Backend:** Spring Boot
**Frontend:** Angular
**Database:** PostgreSQL
