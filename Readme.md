# Savasana - Yoga Fullstack App

This full-stack application is a booking system developed for **Savasana**, a local yoga studio. The app enables users to register and reserve yoga sessions online. It is built with a modern front-end using Angular and a RESTful back-end using Spring Boot. The codebase emphasizes modular design, maintainability, and automated testing with code coverage exceeding 80%.

---

## ⚙️ Technos and Versions

- Java 11
- Node.js 16
- MySQL
- Angular CLI 14.2.1

---

## 🔧 Backend

- **Framework:** Spring Boot 2.6.1
- **Architecture:** RESTful API
- **Database:** MySQL (via `mysql-connector-java`)
- **Authentication:** JWT (`io.jsonwebtoken`)
- **Validation:** Hibernate Validator (`spring-boot-starter-validation`)
- **ORM:** Spring Data JPA
- **Object Mapping:** MapStruct 1.5.1
- **Environment Management:** dotenv-java
- **Code Generation:** Lombok

### ✅ Backend Testing

- Unit & Integration: Spring Boot Test
- Security: Spring Security Test
- Code Coverage: JaCoCo (enforced thresholds)

---

## 🎨 Frontend

- Angular 14.2
- Angular CLI 14.2.1
- Forms: Reactive Forms
- UI: Angular Material

### ✅ Frontend Testing

- **Unit Testing:** Jest (`jest-preset-angular`)
- **E2E Testing:** Cypress 10.4.0
- **Coverage:**
  - Unit: `jest --coverage`
  - E2E: `nyc` + `@cypress/code-coverage`

### 🔧 Build Tools

- Custom Webpack: `@angular-builders/custom-webpack`

---

## ✅ Running Tests

### Frontend Unit Tests

In the `front/` directory:

```bash
npm install
npm run test            # Run all Jest tests
npm run test:watch      # Run tests in watch mode
npm run test:coverage   # Generate unit test coverage report
```
