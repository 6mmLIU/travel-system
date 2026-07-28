# Travel Website System

## Project Introduction
The Travel Website System aims to provide a convenient travel management platform for users and merchants. Users can browse, search, and favorite travel itineraries, while merchants can publish, list/unlist travel itineraries and manage them. The system uses **Spring Boot** for backend development, **Thymeleaf** for frontend page rendering, and **MySQL** for data storage.

## Technology Stack
- **Backend**: Spring Boot, Spring Security, MyBatis, Redis
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Database**: MySQL (Port: `3307`)
- **Tools**: Maven, Lombok

## Feature List
### User Side
✅ User registration and login (Spring Security authentication)  
✅ Travel itinerary search  
✅ Travel itinerary favorite function  
✅ User profile management  
✅ Order management  

### Merchant Side
✅ Travel information publishing  
✅ List/unlist travel itineraries  
✅ Management of published itineraries  
✅ Order processing  

### Admin Side
✅ Backend management functions  
✅ Itinerary review and management  
✅ User management  
✅ Order management  

## Project Structure
```
TravelSystem/
│── src/
│   ├── main/
│   │   ├── java/com/example/travelsystem/
│   │   │   ├── config/         # Configuration classes (Spring Security, Redis, etc.)
│   │   │   ├── controller/     # Controllers
│   │   │   ├── mapper/         # Data access layer
│   │   │   ├── model/          # Entity classes
│   │   │   ├── service/        # Business logic
│   │   │   ├── utils/          # Utility classes
│   │   │   ├── TravelSystemApplication.java  # Startup class
│   │   ├── resources/
│   │   │   ├── mapper/         # MyBatis XML mapping files
│   │   │   ├── static/         # Static resources (CSS/Images)
│   │   │   ├── templates.auth/ # Thymeleaf frontend pages
│   │   │   ├── application.yml # Configuration file
│── pom.xml
│── README.md
```

## Environmental Requirements
- JDK 17+
- MySQL 8+ (Port `3307`)
- Maven 3.8+
- Redis (Optional, used for caching)

## Deployment Steps
### 1. Database Initialization
```sql
CREATE DATABASE travelsystem;
```
Then execute `travelsystem.sql` in the root directory to create the necessary tables:

```sh
mysql -u root -p travelsystem < travelsystem.sql
```
Modify the database configuration in `application.yml` to ensure `spring.datasource.url` points to `jdbc:mysql://localhost:3307/travelsystem`.

### 2. Run Project
```sh
mvn spring-boot:run
```

### 3. Access System
- User Frontend: `http://localhost:8080/`
- Backend Admin: `http://localhost:8080/admin`

## Future Plans
- ✅ Add travel route comment function
- ✅ Implement real-time communication between merchants and users using WebSocket
- ✅ Support multi-language switching

## Contribution Guide
1. Fork this project
2. Create a new branch (`git checkout -b feature-new`)
3. Commit changes (`git commit -m 'Add new feature'`)
4. Push branch (`git push origin feature-new`)
5. Submit a Pull Request

---
💡 **If you have any questions or suggestions regarding this project, feel free to submit an Issue or contact the developer!**
