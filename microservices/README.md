# QMA Microservices Architecture

## 5-Service Architecture with Eureka Service Discovery

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              CLIENT (Frontend)                                    │
│                         http://localhost:3000                                     │
└─────────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      │ HTTP Requests
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                          API GATEWAY (Port 8080)                               │
│                                                                               │
│  Responsibilities:                                                            │
│  • Routes requests to backend services (via Eureka Discovery)                  │
│  • Handles CORS                                                               │
│                                                                               │
│  Routes:                                                                      │
│  • /api/auth/**  ───────────► lb://AUTH-SERVICE                               │
│  • /api/qma/**  ───────────► lb://QMA-SERVICE                                │
└─────────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                       EUREKA SERVER (Port 8761)                               │
│                                                                               │
│  Service Registry & Discovery                                                  │
│  Dashboard: http://localhost:8761                                              │
│                                                                               │
│  Registered Services:                                                         │
│  ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐                   │
│  │   AUTH-SERVICE  │ │   QMA-SERVICE  │ │  API-GATEWAY   │                   │
│  │    (8081)       │ │    (8082)      │ │    (8080)      │                   │
│  └─────────────────┘ └─────────────────┘ └─────────────────┘                   │
└─────────────────────────────────────────────────────────────────────────────────┘
         │                           │
         │                           │
┌────────▼────────┐         ┌────────▼────────┐
│  AUTH SERVICE   │         │   QMA SERVICE    │
│   (Port 8081)  │         │   (Port 8082)   │
│                 │         │                 │
│ • Login/Signup │         │ • Convert       │
│ • JWT Generation│         │ • Compare       │
│ • OAuth2       │         │ • Add/Subtract │
│ • H2 Database  │         │ • Multiply/Divide│
└─────────────────┘         │ • History       │
                           │ • H2 Database   │
                           └────────┬────────┘
                                    │
                                    ▼
                           ┌─────────────────┐
                           │      REDIS       │
                           │   (Port 6379)   │
                           │                 │
                           │ • Cache History │
                           │ • Cache Results │
                           └─────────────────┘
```

## Services Overview

| Service | Port | Description | Docker |
|---------|------|------------|--------|
| **Eureka Server** | 8761 | Service Registry | ✅ |
| **API Gateway** | 8080 | Routing, CORS | ❌ |
| **Auth Service** | 8081 | Login, JWT, OAuth2 | ❌ |
| **QMA Service** | 8082 | Measurements, History | ❌ |
| **Redis** | 6379 | Cache Layer | ✅ |

## How to Run

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker Desktop (for Redis)

### Step 1: Start Redis (Docker)
```bash
docker run -d -p 6379:6379 --name redis redis:alpine
```

### Step 2: Build All Services
```bash
cd microservices
mvn clean install
```

### Step 3: Start Services (in order!)

**Terminal 1 - Eureka Server (MUST START FIRST)**
```bash
cd microservices/eureka-server
mvn spring-boot:run
```
Wait for: "Started EurekaServerApplication in X seconds"
Access Dashboard: http://localhost:8761

**Terminal 2 - Auth Service**
```bash
cd microservices/auth-service
mvn spring-boot:run
```
Wait for: "Started AuthServiceApplication in X seconds"

**Terminal 3 - QMA Service**
```bash
cd microservices/qma-service
mvn spring-boot:run
```
Wait for: "Started QmaServiceApplication in X seconds"

**Terminal 4 - API Gateway**
```bash
cd microservices/api-gateway
mvn spring-boot:run
```
Wait for: "Started ApiGatewayApplication in X seconds"

**Terminal 5 - Frontend**
```bash
cd frontend
npx serve .
```
Access: http://localhost:3000

## Eureka Service Discovery

### What is Eureka?
```
Without Eureka:                         With Eureka:
┌──────────┐                           ┌──────────┐
│ Gateway  │                           │ Gateway  │
│          │                           │          │
│ Hardcoded│                           │ lb://    │
│ URL:8081│                           │ AUTH-    │
└────┬─────┘                           │ SERVICE  │
     │                                  └────┬─────┘
     │                                       │
     ▼                                       ▼
┌──────────┐                          ┌──────────┐
│  Auth    │                         │  Eureka  │
│ Service  │                         │ Server   │
│ (Port 8081)│                       │          │
└──────────┘                          │ Registry │
                                      └────┬─────┘
                                           │
                                      ┌────▼─────┐
                                      │   Auth    │
                                      │  Service  │
                                      │ (Port 8081)│
                                      └───────────┘
```

### Service Registration Flow
```
1. Auth Service starts
       │
       ▼
2. Registers with Eureka
   POST /eureka/apps/AUTH-SERVICE
       │
       ▼
3. Eureka adds to registry
   {
     "instance": {
       "instanceId": "localhost:auth-service:8081",
       "hostName": "localhost",
       "app": "AUTH-SERVICE",
       "ipAddr": "192.168.1.100",
       "port": { "$": 8081 }
     }
   }
       │
       ▼
4. API Gateway queries Eureka
   GET /eureka/apps/AUTH-SERVICE
       │
       ▼
5. Gateway routes to Auth Service
   using instance info
```

## API Endpoints

### Via API Gateway (http://localhost:8080)

#### Auth Endpoints
```bash
# Login
POST /api/auth/login
{
  "username": "john123",
  "password": "secret123"
}

# Signup
POST /api/auth/signup
{
  "username": "john123",
  "email": "john@example.com",
  "password": "secret123"
}

# Google OAuth
POST /api/auth/google
{
  "id": "google-user-id",
  "email": "user@gmail.com",
  "name": "John Doe"
}
```

#### QMA Endpoints
```bash
# Convert (Public)
POST /api/qma/convert
{
  "value": 100,
  "from": "CENTIMETER",
  "to": "INCH"
}

# Compare (Public)
POST /api/qma/compare
{
  "firstValue": 1,
  "firstUnit": "FEET",
  "secondValue": 12,
  "secondUnit": "INCH"
}

# Add (Authenticated)
POST /api/qma/add
Authorization: Bearer <token>
X-User-Id: <userId>
{
  "firstValue": 1,
  "firstUnit": "FEET",
  "secondValue": 6,
  "secondUnit": "INCH"
}

# History (Authenticated)
GET /api/qma/history
Authorization: Bearer <token>
X-User-Id: <userId>
```

## Supported Units

| Type | Units |
|------|-------|
| Length | FEET, INCH, YARD, CENTIMETER, METER |
| Weight | GRAM, KILOGRAM |
| Volume | LITRE, MILLILITRE, GALLON |
| Temperature | CELSIUS, FAHRENHEIT |

## Architecture Benefits

| Feature | Benefit |
|---------|---------|
| **Service Discovery** | Services find each other dynamically |
| **Load Balancing** | Distribute requests across instances |
| **Fault Tolerance** | Circuit breaker patterns |
| **Scalability** | Scale services independently |
| **Caching** | Redis reduces database load |

## Troubleshooting

### Services not registering?
- Wait 10-15 seconds after starting Eureka
- Check Eureka dashboard at http://localhost:8761
- Verify `eureka.client.service-url.defaultZone` is correct

### Redis connection issues?
```bash
# Check if Redis is running
docker ps | grep redis

# Restart Redis
docker restart redis

# Test Redis connection
docker exec redis redis-cli ping
```

### Gateway routing fails?
- Verify all services registered in Eureka
- Check service names match exactly (uppercase)
- Check ports are correct
