# Customer Application

This is a Spring Boot RESTful API for managing customer data. It supports CRUD operations and includes logic to calculate a customer's membership tier based on their annual spending and purchase history.

 Features

- Create, retrieve, update, and delete customers
- Auto-generated UUIDs
- Email format validation
- Real-time tier calculation:
  - **Silver**: annualSpend < $1000
  - **Gold**: $1000 ≤ annualSpend < $10000, last purchase within 12 months
  - **Platinum**: annualSpend ≥ $10000, last purchase within 6 months
- In-memory H2 database
- OpenAPI (Swagger) documentation
- Unit tests for core features

---

 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Web, Spring Data JPA
- H2 Database
- Springdoc OpenAPI
- Maven

---

 Building the Project

Make sure Java 17+ and Maven are installed.

