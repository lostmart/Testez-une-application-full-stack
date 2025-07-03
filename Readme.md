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

### Run a specific test file:

```bash
npm run test -- src/app/app.component.spec.ts
```

## Cypress E2E Tests

E2E tests simulate real user behavior (e.g. login, session creation).

Run interactively:

```bash
npm run cypress:open
```

Run headlessly (e.g. in CI):

```bash
npm run cypress:run
```

Cypress tests are located in `front/cypress/e2e/`
Ensure the Angular app is running on `http://localhost:4200`

## E2E Test Coverage Report

To generate and view end-to-end coverage:

### - Run the instrumented test build:

```bash
npm run e2e:ci
```

### - Generate coverage report:

```bash
npm run e2e:coverage
```

### - View the report in your browser:

```bash
npm run e2e:coverage:open
```

Then visit: http://localhost:4300

## ☕ Backend Tests

From the back/ directory:

`./mvnw verify`

View the JaCoCo HTML report:

`back/target/site/jacoco/index.html`

## 🧩 Setup Notes

### 1. Frontend

```bash
cd front
npm install
npm start
```

### 2. Backend

- Requires Java 11+, Maven, and a running MySQL instance
- Configure `.env` or `application.properties` with:
  - MySQL credentials
  - JWT secret

### 3. Cypress

- App must run on `http://localhost:4200`
- Cypress config: `front/cypress.config.ts`
- TypeScript support: `front/cypress/tsconfig.json`
