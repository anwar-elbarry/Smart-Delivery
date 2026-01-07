# 📦 Smart Delivery Management System (SDMS)

## 📋 Table of Contents
- [Project Overview](#-project-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Database Schema](#-database-schema)
- [Installation & Setup](#-installation--setup)
- [API Documentation](#-api-documentation)
- [User Roles & Stories](#-user-roles--stories)
- [Project Structure](#-project-structure)

---

## 🎯 Project Overview

**Smart Delivery Management System (SDMS)** is a comprehensive logistics management solution developed for **SmartLogi**, a delivery company operating across Morocco. The system modernizes and automates delivery operations, replacing manual Excel spreadsheets and paper-based processes.

### Context
SmartLogi faced several operational challenges:
- ❌ Manual data entry errors
- ❌ Data loss and inconsistencies
- ❌ Delivery delays
- ❌ Lack of real-time package tracking
- ❌ Inefficient route planning

### Solution
SDMS provides a centralized web-based platform that:
- ✅ Manages package collection from sender clients
- ✅ Tracks temporary storage in warehouses
- ✅ Plans and optimizes delivery routes by geographic zones
- ✅ Provides complete package traceability with status history
- ✅ Reduces human errors and improves delivery reliability

---

## ✨ Features

### Core Functionality
- **📦 Package Management**: Create, update, track, and manage packages with multi-product support
- **👥 User Management**: Handle senders, recipients, delivery personnel, and logistics managers
- **🚚 Delivery Personnel Management**: Assign delivery zones and vehicles to drivers
- **🗺️ Zone Management**: Organize deliveries by geographic zones and postal codes
- **📊 Advanced Filtering**: Filter packages by status, zone, city, priority, and date
- **🔍 Search**: Full-text search across packages, clients, and delivery personnel
- **📈 Statistics**: Calculate total weight and package count per driver and zone
- **📜 History Tracking**: Complete audit trail of package status changes
- **🎨 Priority Management**: Handle urgent, medium, and low-priority deliveries
- **📄 Pagination & Sorting**: Efficient data display for large datasets

### Package Status Flow
```
CREATED → COLLECTED → IN_STOCK → IN_TRANSIT → DELIVERED
```

---

## 🛠️ Technology Stack

### Backend
| Technology | Version/Tool | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 3.5.7 | Application Framework |
| **Maven** | 3.8+ | Build & Dependency Management |
| **PostgreSQL** | 12+ | Relational Database |
| **Spring Data JPA** | - | ORM Framework |
| **Liquibase** | 5.0.1 | Database Migration |
| **Spring Security** | - | Authentication & Authorization |
| **JWT (Java JWT)** | 0.12.6 | Secure API Tokens |
| **SpringDoc OpenAPI**| 2.8.13 | API Documentation (Swagger) |
| **MapStruct** | 1.6.3 | Object Mapping |
| **Lombok** | 1.18.34 | Boilerplate Code Reduction |
| **Spring Data Redis**| - | Caching |
| **Spring Boot Mail**| - | Email Notifications |
| **Dotenv** | 4.0.0 | Environment Variable Management |

### Architecture Patterns
- ✅ RESTful API Design
- ✅ Layered Architecture (Controller → Service → Repository)
- ✅ DTO Pattern with MapStruct
- ✅ Centralized Exception Handling
- ✅ Repository Pattern with Spring Data JPA
- ✅ Role-Based Access Control (RBAC)

---

## 🏗️ Architecture

### Layered Architecture
```
┌─────────────────────────────────────────┐
│   Controller Layer (REST Endpoints)    │
│   - ColisController                     │
│   - UserController                      │
│   - LivreurController                   │
│   - ProduitController                   │
│   - ZoneController                      │
├─────────────────────────────────────────┤
│   Service Layer (Business Logic)       │
│   - ColisService                        │
│   - UserService                         │
│   - LivreurService                      │
│   - ProduitService                      │
│   - ZoneService                         │
├─────────────────────────────────────────┤
│   Repository Layer (Data Access)       │
│   - ColisRepository                     │
│   - UserRepository                      │
│   - LivreurRepository                   │
│   - ProduitRepository                   │
│   - ZoneRepository                      │
├─────────────────────────────────────────┤
│   Database Layer (PostgreSQL)          │
└─────────────────────────────────────────┘
```

### Project Structure
```
org.example.smart_delivery/
├── controller/          # REST API endpoints
├── service/            # Business logic layer
├── repository/         # Data access layer (Spring Data JPA)
├── entity/             # JPA entities
│   └── enums/         # Enums (ColisStatus, Priority, UserRole)
├── dto/                # Data Transfer Objects
│   ├── request/       # Request DTOs
│   └── response/      # Response DTOs
├── mapper/            # MapStruct mappers
│   ├── request/      # Request mapping
│   └── response/     # Response mapping
├── exception/         # Custom exceptions & global handler
└── Application.java   # Spring Boot entry point
```

---

## 🗄️ Database Schema

### Entity Relationship Diagram
```
┌─────────────┐          ┌─────────────┐
│    User     │          │    Zone     │
└─────────────┘          └─────────────┘
      │ │                       │
      │ │                       │
      │ └─────────┐             │
      │           │             │
      ▼           ▼             ▼
┌─────────────────────────────────┐
│           Colis                 │
│  (Package Management)           │
└─────────────────────────────────┘
      │                    │
      ▼                    ▼
┌──────────────┐    ┌──────────────┐
│HistoriqueLiv│    │ColisProduit  │
└──────────────┘    └──────────────┘
                           │
                           ▼
                    ┌──────────────┐
                    │   Produit    │
                    └──────────────┘
```

### Tables

#### **users**
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| nom | VARCHAR | NOT NULL |
| prenom | VARCHAR | NOT NULL |
| email | VARCHAR | UNIQUE, NOT NULL |
| telephone | VARCHAR | |
| adress | VARCHAR | |
| role | ENUM | (EXPEDITEUR, LIVREUR, GESTIONNAIRE, DESTINATAIRE) |

#### **livreurs** (Delivery Personnel)
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| user_id | UUID | FOREIGN KEY → users(id) |
| vehicule | VARCHAR | |
| zone_assignee_id | UUID | FOREIGN KEY → zone(id) |

#### **colis** (Packages)
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| description | TEXT | |
| poids | DECIMAL | NOT NULL |
| statut | ENUM | (CREATED, COLLECTED, IN_STOCK, IN_TRANSIT, DELIVERED) |
| priorite | ENUM | (HIGH, MEDIUM, LOW) |
| ville_destination | VARCHAR | |
| livreur_id | UUID | FOREIGN KEY → livreurs(id) |
| client_expediteur_id | UUID | FOREIGN KEY → users(id) |
| destinataire_id | UUID | FOREIGN KEY → users(id) |
| id_zone | UUID | FOREIGN KEY → zone(id) |

#### **produits** (Products)
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| nom | VARCHAR | NOT NULL |
| categorie | VARCHAR | |
| poids | DOUBLE | |
| prix | DECIMAL | |

#### **colis_produit** (Package-Product Association)
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| colis_id | UUID | FOREIGN KEY → colis(id) |
| produit_id | UUID | FOREIGN KEY → produits(id) |
| quantite | INTEGER | |
| date_ajout | TIMESTAMP | |
| prix | DECIMAL | |

#### **zone** (Geographic Zones)
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| nome | VARCHAR | NOT NULL |
| code_postal | INTEGER | |

#### **historique_livraison** (Delivery History)
| Column | Type | Constraints |
|--------|------|-------------|
| id | UUID | PRIMARY KEY |
| colis_id | UUID | FOREIGN KEY → colis(id) |
| statut | ENUM | ColisStatus |
| date_changement | TIMESTAMP | |
| commentaire | TEXT | |

---

## 🚀 Installation & Setup

### Prerequisites
- ☑️ **Java 21** or higher
- ☑️ **Maven 3.8+**
- ☑️ **PostgreSQL 12+**
- ☑️ **Git**

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone <your-repository-url>
   cd "Smart Delivery V0.1.0"
   ```

2. **Configure Database**
   - Create PostgreSQL database:
     ```sql
     CREATE DATABASE smart_delivery;
     ```
   
   - Update `src/main/resources/application.yaml`:
     ```yaml
     spring:
       datasource:
         url: jdbc:postgresql://localhost:5432/smart_delivery
         username: YOUR_DB_USERNAME
         password: YOUR_DB_PASSWORD
     ```

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run Database Migrations**
   Liquibase migrations run automatically on application startup.

5. **Start the Application**
   ```bash
   mvn spring-boot:run
   ```

6. **Verify Installation**
   - **Application**: `http://localhost:8080`
   - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
   - **API Docs**: `http://localhost:8080/v3/api-docs`

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

All APIs are documented using OpenAPI 3.0 (Swagger). Access interactive documentation at:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints Overview

#### **📦 Colis (Packages)** - `/api/colis`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/` | Create new package | ColisDTO |
| PUT | `/{coliId}` | Update package | ColisDTO |
| GET | `/` | Get all packages (paginated) | - |
| PUT | `/assign` | Assign package to delivery person | `colisId`, `livreurId` |
| PUT | `/colisRequest` | Create package with products | `expedId`, `distenId`, `produitIds[]` |
| GET | `/filter` | Filter packages | `zoneId`, `status`, `priority` |
| GET | `/search` | Search packages | `q` (query string) |
| GET | `/calcule` | Calculate delivery stats | `livreurId` |

**Example: Create Package**
```bash
POST /api/colis
Content-Type: application/json

{
  "description": "Electronics package",
  "poids": 2.5,
  "priorite": "HIGH",
  "villeDestination": "Casablanca",
  "clientExpediteurId": "uuid-here",
  "destinataireId": "uuid-here",
  "zoneId": "uuid-here"
}
```

**Example: Assign Package**
```bash
PUT /api/colis/assign?colisId=xxx&livreurId=yyy
```

**Example: Calculate Stats**
```bash
GET /api/colis/calcule?livreurId=xxx

Response:
{
  "count": 15,
  "poids": 125.50
}
```

#### **👥 Users** - `/api/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create new user |
| PUT | `/{id}` | Update user |
| GET | `/{id}` | Get user by ID |
| GET | `/` | Get all users |
| DELETE | `/{id}` | Delete user |
| GET | `/search` | Search users |

#### **🚚 Livreurs (Delivery Personnel)** - `/api/Livreurs`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create delivery person |
| PUT | `/{id}` | Update delivery person |
| GET | `/{id}` | Get delivery person by ID |
| GET | `/` | Get all delivery personnel |
| DELETE | `/{id}` | Delete delivery person |

#### **📦 Produits (Products)** - `/api/produits`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create new product |
| PUT | `/{produitId}` | Update product |
| GET | `/{produitId}` | Get product by ID |
| GET | `/` | Get all products (paginated) |
| DELETE | `/{produitId}` | Delete product |

#### **🗺️ Zones** - `/api/zones`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create new zone |
| PUT | `/{id}` | Update zone |
| GET | `/{id}` | Get zone by ID |
| GET | `/` | Get all zones |
| DELETE | `/{id}` | Delete zone |

### Pagination & Sorting
Most list endpoints support pagination and sorting:
```
GET /api/colis?page=0&size=10&sortBy=id&sortDir=asc
```

Parameters:
- `page`: Page number (default: 0)
- `size`: Items per page (default: 10)
- `sortBy`: Field to sort by (default: id)
- `sortDir`: Sort direction (`asc` or `desc`)

### Filtering
Filter packages by multiple criteria:
```
GET /api/colis/filter?zoneId=xxx&status=IN_TRANSIT&priority=HIGH
```

### Search
Full-text search across multiple fields:
```
GET /api/colis/search?q=casablanca
GET /api/users/search?q=john
```

---

## 👥 User Roles & Stories

### **Client Expéditeur (Sender Client)** - Role: `EXPEDITEUR`
**User Stories:**
- ✅ Create delivery requests for sending packages
- ✅ View list of sent packages (in progress and delivered)
- ✅ Track package status in real-time
- 🔔 Receive email notifications (planned feature)

### **Destinataire (Recipient)** - Role: `DESTINATAIRE`
**User Stories:**
- ✅ View status of incoming packages
- ✅ Track expected delivery times
- ✅ View package details

### **Livreur (Delivery Personnel)** - Role: `LIVREUR`
**User Stories:**
- ✅ View assigned packages with priorities and zones
- ✅ Update package status during collection and delivery
- ✅ Access delivery route information
- ✅ View package weight and destination

### **Gestionnaire Logistique (Logistics Manager)** - Role: `GESTIONNAIRE`
**User Stories:**
- ✅ View all delivery requests
- ✅ Assign packages to delivery personnel
- ✅ Filter and paginate packages by status, zone, city, priority, date
- ✅ Search packages, clients, and delivery personnel by keyword
- ✅ Calculate total weight and package count per driver and zone
- ✅ Identify late or priority packages
- ✅ Associate multiple products to a package
- ✅ View complete package history with comments
- ✅ Edit or delete erroneous data
- 🔔 Receive email alerts (planned feature)

---

## 📁 Project Structure

```
Smart Delivery V0.1.0/
├── src/
│   ├── main/
│   │   ├── java/org/example/smart_delivery/
│   │   │   ├── controller/
│   │   │   │   ├── ColisController.java
│   │   │   │   ├── UserController.java
│   │   │   │   ├── LivreurController.java
│   │   │   │   ├── ProduitController.java
│   │   │   │   └── ZoneController.java
│   │   │   ├── service/
│   │   │   │   ├── colis/
│   │   │   │   │   ├── ColisService.java
│   │   │   │   │   ├── ColisServiceImpl.java
│   │   │   │   │   ├── Colisfilter.java
│   │   │   │   │   └── Coliscounter.java
│   │   │   │   ├── user/
│   │   │   │   ├── livreur/
│   │   │   │   ├── produit/
│   │   │   │   ├── zone/
│   │   │   │   └── historique/
│   │   │   ├── repository/
│   │   │   │   ├── ColisRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── LivreurRepository.java
│   │   │   │   ├── ProduitRepository.java
│   │   │   │   ├── ZoneRepository.java
│   │   │   │   ├── ColisProduitRepository.java
│   │   │   │   └── HistoriqueLivraisonRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── Colis.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Livreur.java
│   │   │   │   ├── Produit.java
│   │   │   │   ├── ColisProduit.java
│   │   │   │   ├── Zone.java
│   │   │   │   ├── HistoriqueLivraison.java
│   │   │   │   └── enums/
│   │   │   │       ├── ColisStatus.java
│   │   │   │       ├── Priority.java
│   │   │   │       └── UserRole.java
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── mapper/
│   │   │   │   ├── request/
│   │   │   │   ├── response/
│   │   │   │   └── RefMapper.java
│   │   │   ├── exception/
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── Application.java
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── db/
│   │           └── changelog/
│   │               └── db.changelog-master.yaml
│   └── test/
├── pom.xml
└── README.md
```

---

## 📝 Configuration

### Application Configuration (`application.yaml`)
```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/smart_delivery
    username: postgres
    password: YOUR_PASSWORD
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: none  # Managed by Liquibase
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect

  liquibase:
    change-log: classpath:db/changelog/db.changelog-master.yaml

springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /v3/api-docs
```

---

## 🧪 Testing

### Run Tests
```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

### Test Structure
```
src/test/java/
└── org/example/smart_delivery/
    ├── controller/     # Controller tests
    ├── service/        # Service tests
    └── repository/     # Repository tests
```

---

## 🔐 Security Considerations

**⚠️ Note**: Current version does not implement authentication/authorization.

### For Production Deployment:
- [ ] Implement Spring Security
- [ ] Add JWT or OAuth2 authentication
- [ ] Role-based access control (RBAC)
- [ ] Encrypt sensitive data
- [ ] Enable HTTPS
- [ ] Input sanitization
- [ ] Rate limiting
- [ ] API key management

---

## 🚀 Future Enhancements

### Planned Features
- [ ] **Email Notifications**: SMTP integration for delivery updates
- [ ] **Real-time Tracking**: WebSocket for live package tracking
- [ ] **Mobile Application**: iOS/Android apps for drivers
- [ ] **Barcode Scanning**: QR code/barcode integration
- [ ] **Route Optimization**: AI-powered route planning
- [ ] **Analytics Dashboard**: Visual reports and insights
- [ ] **PDF Invoices**: Automated invoice generation
- [ ] **SMS Notifications**: SMS alerts for key events
- [ ] **Payment Integration**: Online payment processing
- [ ] **Multi-language**: French/Arabic support

### Technical Improvements
- [ ] Redis caching for performance
- [ ] Docker containerization
- [ ] CI/CD pipeline (GitHub Actions/Jenkins)
- [ ] Kubernetes orchestration
- [ ] Microservices architecture
- [ ] Event-driven architecture with Kafka
- [ ] GraphQL API

---

## 🤝 Contributing

### Development Workflow
1. Fork the repository
2. Create feature branch: `git checkout -b feature/AmazingFeature`
3. Commit changes: `git commit -m 'Add AmazingFeature'`
4. Push to branch: `git push origin feature/AmazingFeature`
5. Open a Pull Request

### Code Style Guidelines
- ✅ Follow Java naming conventions
- ✅ Use Lombok annotations appropriately
- ✅ Write meaningful commit messages
- ✅ Add JavaDoc for public APIs
- ✅ Ensure all tests pass
- ✅ Follow SOLID principles
- ✅ Keep methods small and focused

### Commit Message Format
```
type(scope): subject

body

footer
```

Types: `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

Example:
```
feat(colis): add package statistics endpoint

Implemented calcule endpoint to return total weight and count
of packages assigned to a delivery person.

Closes #123
```

---

## 📊 Project Metrics

| Metric | Count |
|--------|-------|
| Controllers | 5 |
| Services | 7 |
| Repositories | 7 |
| Entities | 7 |
| Enums | 3 |
| DTOs | 14+ |
| Mappers | 15+ |
| Database Tables | 7 |

---

## 📖 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [Liquibase Documentation](https://docs.liquibase.com/)
- [MapStruct Reference](https://mapstruct.org/documentation/)
- [OpenAPI Specification](https://swagger.io/specification/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 📞 Support & Contact

For questions, issues, or contributions:
- **Email**: elbarry.anouar.contact@gmail.com
---

## 👏 Acknowledgments

- Spring community for excellent documentation
- Open-source contributors

---

**Version**: 0.1.0  
**Last Updated**: November 9, 2025  
**Status**: ✅ Active Development

---

Made with ❤️ 
