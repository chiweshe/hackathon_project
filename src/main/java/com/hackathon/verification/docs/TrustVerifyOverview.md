# TrustVerify - Comprehensive Verification Platform

## Project Overview

TrustVerify is a comprehensive verification platform designed to validate the legitimacy of land, vehicles, and landlord/tenant relationships. The application helps users verify if properties and vehicles are legitimate before purchase and allows landlords and tenants to check each other's reputation.

## Core Features

### Land Verification
- Verify if a stand/property exists
- Check if the property is already allocated
- Validate property ownership details
- AI-enhanced verification with confidence scores

### Vehicle Verification
- Verify vehicle legitimacy using chassis number
- Check vehicle history (previous owners, theft records)
- Detect potential tampering or modifications
- Report vehicles as stolen or tampered
- AI-enhanced verification with confidence scores

### Landlord/Tenant Verification
- Rating system for landlords
- Rating system for tenants
- Historical record of tenant/landlord behavior
- Sentiment analysis of reviews

## AI Integration

The application incorporates AI for:
- Pattern recognition in fraudulent property listings
- Anomaly detection in vehicle history
- Sentiment analysis for landlord/tenant reviews
- Predictive analysis for risk assessment
- Confidence scoring for verification results

## Technical Architecture

### Entity Models
- **Land**: Represents a land property with details like stand number, location, title, owner information, and allocation status.
- **Vehicle**: Represents a vehicle with details like chassis number, registration number, make, model, owner information, and status (stolen/tampered).
- **Landlord**: Represents a landlord with personal details, verification status, and rating information.
- **Tenant**: Represents a tenant with personal details, employment information, verification status, and rating information.
- **Rating**: Represents a rating given by a landlord to a tenant or vice versa, including rating value, review text, and sentiment score.

### Repository Layer
Each entity has a corresponding repository interface that extends JpaRepository to provide CRUD operations and custom query methods.

### Service Layer
The service layer implements the business logic for each verification domain:
- **LandService**: Provides methods for land verification and management.
- **VehicleService**: Provides methods for vehicle verification, reporting, and management.
- **LandlordService**: Provides methods for landlord verification and rating.
- **TenantService**: Provides methods for tenant verification and rating.

### Controller Layer
The controller layer exposes RESTful endpoints for each verification domain:
- **LandController**: Exposes endpoints for land verification and management.
- **VehicleController**: Exposes endpoints for vehicle verification, reporting, and management.
- **LandlordController**: Exposes endpoints for landlord verification and rating.
- **TenantController**: Exposes endpoints for tenant verification and rating.

### Exception Handling
The application includes custom exception handling to provide meaningful error messages:
- **VerificationException**: Custom exception for verification-related errors.
- **VerificationExceptionHandler**: Global exception handler for verification exceptions.

## API Documentation

The API is documented using OpenAPI/Swagger. You can access the API documentation at:
- Local development: http://localhost:8080/swagger-ui.html
- Production: https://api.trustverify.com/swagger-ui.html

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Installation
1. Clone the repository
2. Configure the database connection in `application.yml`
3. Run `mvn clean install` to build the application
4. Run `mvn spring-boot:run` to start the application

### Using the API

#### Land Verification
```
POST /api/lands/verify
{
  "standNumber": "12345",
  "location": "Harare"
}
```

#### Vehicle Verification
```
POST /api/vehicles/verify
{
  "chassisNumber": "ABC123456789",
  "registrationNumber": "XYZ123"
}
```

#### Landlord/Tenant Verification
```
POST /api/landlords/verify
{
  "idNumber": "12345678"
}

POST /api/tenants/verify
{
  "idNumber": "87654321"
}
```

## Future Enhancements

1. **Mobile Application**: Develop mobile applications for Android and iOS to make verification more accessible.
2. **Blockchain Integration**: Implement blockchain technology for immutable record-keeping of property and vehicle ownership.
3. **Advanced AI Models**: Enhance AI capabilities with more sophisticated models for fraud detection and risk assessment.
4. **Integration with Government Databases**: Connect with official government databases for more authoritative verification.
5. **Real-time Alerts**: Implement real-time alerts for stolen vehicles and fraudulent property listings.

## Conclusion

TrustVerify provides a comprehensive solution for verification needs in land purchases, vehicle acquisitions, and rental agreements. By leveraging AI and a robust verification system, it helps users make informed decisions and avoid potential fraud or issues.