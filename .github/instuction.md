## MobileShop application - Instruction

This document defines the instruction and acceptance criteria to create a MobileShop Java application that accepts mobile and customer JSON input, stores purchases in an internal database, and exposes REST endpoints for testing. It also incorporates the non-functional and architectural constraints provided.

### Non-functional requirements

- Java version: 25 or higher
- Build tool: Gradle 9.1.0 or higher (use Gradle wrapper files in the repo)
- Base package: `com.training.demo.tribble`
- Main class name: `MobileShopApplication` (must exist in the base package)
- Configuration file: `src/main/resources/application.properties`
- Input data files: `src/main/resources/input` (JSON payloads for testing)
- Output & logging directory: `src/main/resources/output` (app may write files here in local runs)
- Required logging provider: `org.slf4j:slf4j-jdk-platform-logging:2.0.17` or higher
- Logging configuration must be stored in `application.properties` with keys prefixed by `logging.slf4j.`

### Architecture & patterns (required)

- Layered architecture using Java interfaces for controller, service and repository layers.
- Persistence layer uses repository interfaces and implementations (Repository pattern).
- DTO (Data Transfer Object) pattern for ingesting external JSON.
- Domain model classes must be JPA entities for persistence layer compatibility.
- Use logging at DEBUG/INFO/ERROR levels throughout the application.

### Internal DB

- Use an embedded H2 database for development and tests (in-memory with file option available). Configure the datasource in `application.properties`.

### Functional requirements (MobileShop)

1. Domain entities (package `com.training.demo.tribble.domain`):

   These must be JPA entities. Below are explicit field definitions, relationship guidance, and example Java classes you can copy into the project. Use `UUID` for primary keys and annotate with `@Id` + `@GeneratedValue` (where appropriate). Keep entities in `com.training.demo.tribble.domain`.

    - Mobile (entity)
        - Fields:
            - `UUID id`
            - `String brand`
            - `String model`
            - `String imei` (unique)
            - `BigDecimal price`
            - `String color`
            - `int stockQuantity`
        - Notes: Add a unique constraint on `imei`.

      Example guidance for `Mobile` entity:

        - Location: `com.training.demo.tribble.domain.Mobile`
        - Annotate the class with `@Entity` and `@Table(name = "mobiles")`.
        - Use a `UUID` primary key field `id` initialized with `UUID.randomUUID()` and annotated with `@Id`.
        - Add a unique constraint on the `imei` column and annotate `imei` with `@Column(nullable=false, unique=true)`.
        - Provide getters/setters and at least a no-args constructor (or use Lombok if allowed).

    - Customer (entity)
        - Fields:
            - `UUID id`
            - `String firstName`
            - `String lastName`
            - `String email` (index/unique optional)
            - `String phone`
            - `String address`

      Example guidance for `Customer` entity:

        - Location: `com.training.demo.tribble.domain.Customer`
        - Annotate with `@Entity` and `@Table(name = "customers")`.
        - Use `UUID id` as primary key annotated with `@Id` and initialized by default.
        - Define fields: `firstName`, `lastName`, `email`, `phone`, `address`.
        - Consider indexing or making `email` unique if the business requires it.

    - Purchase (entity)
        - Fields:
            - `UUID id`
            - Reference to `Mobile` (ManyToOne) or mobile snapshot fields
            - Reference to `Customer` (ManyToOne)
            - `LocalDateTime purchasedAt`
            - `int quantity`
            - `BigDecimal totalPrice`

      Example guidance for `Purchase` entity:

        - Location: `com.training.demo.tribble.domain.Purchase`
        - Annotate with `@Entity` and `@Table(name = "purchases")`.
        - Use `UUID id` as primary key annotated with `@Id`.
        - Reference `Mobile` and `Customer` using `@ManyToOne` relationships and `@JoinColumn` for foreign keys (e.g. `mobile_id`, `customer_id`). Mark them `optional=false` if required.
        - Define `purchasedAt` (LocalDateTime), `quantity` (int), and `totalPrice` (BigDecimal).
        - Optionally include snapshot fields for mobile details if historical prices must be preserved.

      Implementation notes:
        - Use `jakarta.persistence` annotations (Spring Boot 3+).
        - Keep entities simple (no heavy business logic). Business rules belong in services.
        - For `Purchase`, you can store a snapshot of mobile (brand/model/price) in the purchase if historical pricing is required.

   Implementation notes:
    - Use `jakarta.persistence` annotations (Spring Boot 3+).
    - Keep entities simple (no heavy business logic). Business rules belong in services.
    - For `Purchase`, you can store a snapshot of mobile (brand/model/price) in the purchase if historical pricing is required.


2. DTOs (package `com.training.demo.tribble.dto`):
    - `MobileDto` (record or class matching mobile JSON)
    - `CustomerDto` (record/class matching customer JSON)
    - `PurchaseRequestDto` containing `MobileDto`, `CustomerDto`, and `quantity`

3. Repositories (package `com.training.demo.tribble.repository`):
    - `MobileRepository`, `CustomerRepository`, `PurchaseRepository` as interfaces. You may use Spring Data JPA or provide manual implementations using `EntityManager`.

4. Services (package `com.training.demo.tribble.service`):
    - Service interfaces and implementations to:
        - validate DTOs
        - persist entities
        - manage mobile stock
        - calculate total prices

5. Controllers (package `com.training.demo.tribble.controller`):
    - `POST /api/mobiles` - add or update mobile inventory (accepts `MobileDto`)
    - `GET /api/mobiles` - list mobiles
    - `POST /api/customers` - create customer (accepts `CustomerDto`)
    - `GET /api/customers` - list customers
    - `POST /api/purchases` - create a purchase (accepts `PurchaseRequestDto`) - stores Purchase and reduces Mobile stock
    - `GET /api/purchases/{id}` - get purchase details

### Sample input JSON files (place in `src/main/resources/input`)

- `mobile.json`:
```json
{
	"brand": "Acme",
	"model": "A1",
	"imei": "356938035643809",
	"price": 499.99,
	"color": "Black",
	"stockQuantity": 10
}
```

- `customer.json`:
```json
{
	"firstName": "Jane",
	"lastName": "Doe",
	"email": "jane.doe@example.com",
	"phone": "555-0100",
	"address": "123 Main St, Anytown, USA"
}
```

- `purchase.json`:
```json
{
	"mobile": { /* mobile fields */ },
	"customer": { /* customer fields */ },
	"quantity": 1
}
```

### application.properties (required - `src/main/resources/application.properties`)

Minimum recommended settings:

```
spring.application.name=mobile-shop
spring.datasource.url=jdbc:h2:mem:mobileshop;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Logging provider settings use prefix "logging.slf4j."
logging.slf4j.simpleLogger.defaultLogLevel=info
logging.slf4j.simpleLogger.showDateTime=true
logging.slf4j.simpleLogger.dateTimeFormat=yyyy-MM-dd HH:mm:ss

# Input/output directories for the app (used by tests or utilities)
app.data.input=src/main/resources/input
app.data.output=src/main/resources/output
```

### Dependencies (Gradle)

Required dependencies (add to `build.gradle` or `build.gradle.kts`):

- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `com.h2database:h2`
- `org.springframework.boot:spring-boot-starter-test` (test scope)
- `org.slf4j:slf4j-jdk-platform-logging:2.0.17` (or higher)

### Build file guidance

Use the Gradle wrapper and configure Java toolchain for Java 25. Example (Groovy DSL skeleton):

```groovy
plugins {
		id 'org.springframework.boot' version '3.1.5'
		id 'io.spring.dependency-management' version '1.1.0'
		id 'java'
}

java {
		toolchain {
				languageVersion = JavaLanguageVersion.of(25)
		}
}

dependencies {
		implementation 'org.springframework.boot:spring-boot-starter-web'
		implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
		runtimeOnly 'com.h2database:h2'
		implementation 'org.slf4j:slf4j-jdk-platform-logging:2.0.17'
		testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.withType(Test) {
		useJUnitPlatform()
}
```

### Acceptance tests / verification

- Build: `./gradlew.bat clean build` (Windows) or `./gradlew clean build` (Unix)
- Run: `./gradlew.bat bootRun` and verify endpoints are reachable on port 8080
- Test with sample JSON files using PowerShell `Invoke-RestMethod` or `curl` (examples below)

PowerShell examples (Windows):

```powershell
# POST a mobile
Invoke-RestMethod -Uri http://localhost:8080/api/mobiles -Method Post -ContentType 'application/json' -Body (Get-Content -Raw src\main\resources\input\mobile.json)

# POST a customer
Invoke-RestMethod -Uri http://localhost:8080/api/customers -Method Post -ContentType 'application/json' -Body (Get-Content -Raw src\main\resources\input\customer.json)

# POST a purchase
Invoke-RestMethod -Uri http://localhost:8080/api/purchases -Method Post -ContentType 'application/json' -Body (Get-Content -Raw src\main\resources\input\purchase.json)
```

Or with `curl`:

```bash
curl -X POST -H 'Content-Type: application/json' --data @src/main/resources/input/purchase.json http://localhost:8080/api/purchases
```

### Deliverables (to implement in code)

- `build.gradle` + Gradle wrapper files
- `src/main/java/com/training/demo/tribble/MobileShopApplication.java` (Spring Boot main class)
- Entities: `Mobile`, `Customer`, `Purchase` under `com.training.demo.tribble.domain`
- DTOs under `com.training.demo.tribble.dto`
- Repositories under `com.training.demo.tribble.repository`
- Services under `com.training.demo.tribble.service`
- Controllers under `com.training.demo.tribble.controller`
- Sample JSON files under `src/main/resources/input`
- `src/main/resources/application.properties` with H2 and logging settings

### Notes & next steps

- The main class must be named `MobileShopApplication` inside the base package so Spring's component scan finds components.
    1) Create full skeleton (recommended) — I will create all source files and sample JSON and run a quick build.


