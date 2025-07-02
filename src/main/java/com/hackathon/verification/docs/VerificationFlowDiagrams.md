# TrustVerify - Verification Flow Diagrams

This document outlines the verification flows for the three main verification domains in the TrustVerify application.

## 1. Land Verification Flow

```
┌─────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  User Input  │────▶│ Verify Request  │────▶│ Database Lookup │────▶│ Basic Response  │
│ Stand Number │     │    Location     │     │                 │     │                 │
└─────────────┘     └─────────────────┘     └─────────────────┘     └────────┬────────┘
                                                                             │
                                                                             ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Final Response │◀────│ Format Response │◀────│  AI Processing  │◀────│ Confidence Score│
│  with Details   │     │                 │     │                 │     │   Calculation   │
└─────────────────┘     └─────────────────┘     └─────────────────┘     └─────────────────┘
```

### Process Description:

1. **User Input**: User provides a stand number and location for verification.
2. **Verification Request**: System creates a verification request with the provided details.
3. **Database Lookup**: System searches the database for matching land records.
4. **Basic Response**: System generates a basic response based on the database lookup.
5. **Confidence Score Calculation**: AI system calculates a confidence score for the verification.
6. **AI Processing**: AI enhances the verification with additional insights.
7. **Format Response**: System formats the response with all verification details.
8. **Final Response**: User receives the final verification response with details about the land's existence, allocation status, and ownership information.

## 2. Vehicle Verification Flow

```
┌─────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  User Input  │────▶│ Verify Request  │────▶│ Database Lookup │────▶│ Basic Response  │
│Chassis Number│     │  Registration   │     │                 │     │                 │
└─────────────┘     └─────────────────┘     └─────────────────┘     └────────┬────────┘
                                                                             │
                                                                             ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Final Response │◀────│ Format Response │◀────│  AI Processing  │◀────│ Ownership History│
│  with Details   │     │                 │     │                 │     │   Generation    │
└─────────────────┘     └─────────────────┘     └─────────────────┘     └─────────────────┘
```

### Process Description:

1. **User Input**: User provides a chassis number or registration number for verification.
2. **Verification Request**: System creates a verification request with the provided details.
3. **Database Lookup**: System searches the database for matching vehicle records.
4. **Basic Response**: System generates a basic response based on the database lookup.
5. **Ownership History Generation**: System generates the ownership history of the vehicle.
6. **AI Processing**: AI enhances the verification with additional insights, including stolen/tampered status analysis.
7. **Format Response**: System formats the response with all verification details.
8. **Final Response**: User receives the final verification response with details about the vehicle's existence, ownership history, and status (stolen/tampered).

## 3. Landlord/Tenant Verification Flow

```
┌─────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  User Input  │────▶│ Verify Request  │────▶│ Database Lookup │────▶│ Basic Response  │
│  ID Number   │     │                 │     │                 │     │                 │
└─────────────┘     └─────────────────┘     └─────────────────┘     └────────┬────────┘
                                                                             │
                                                                             ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Final Response │◀────│ Format Response │◀────│Sentiment Analysis│◀────│ Rating Analysis │
│  with Details   │     │                 │     │   of Reviews    │     │                 │
└─────────────────┘     └─────────────────┘     └─────────────────┘     └─────────────────┘
```

### Process Description:

1. **User Input**: User provides an ID number of a landlord or tenant for verification.
2. **Verification Request**: System creates a verification request with the provided details.
3. **Database Lookup**: System searches the database for matching landlord/tenant records.
4. **Basic Response**: System generates a basic response based on the database lookup.
5. **Rating Analysis**: System analyzes the ratings given to the landlord/tenant.
6. **Sentiment Analysis**: AI performs sentiment analysis on the reviews to provide deeper insights.
7. **Format Response**: System formats the response with all verification details.
8. **Final Response**: User receives the final verification response with details about the landlord/tenant's reputation, rating history, and AI-generated insights.

## 4. AI Integration in Verification Processes

The TrustVerify application integrates AI in all verification processes to enhance the accuracy and reliability of the verification results. The AI components include:

1. **Confidence Score Calculation**: AI calculates a confidence score for each verification based on the available data and its quality.
2. **Pattern Recognition**: AI identifies patterns in fraudulent property listings and vehicle records.
3. **Anomaly Detection**: AI detects anomalies in vehicle history and landlord/tenant behavior.
4. **Sentiment Analysis**: AI analyzes the sentiment of reviews to provide deeper insights into landlord/tenant relationships.
5. **Predictive Analysis**: AI predicts potential risks based on historical data.

The AI integration allows TrustVerify to provide more accurate and reliable verification results, helping users make informed decisions when buying land, purchasing vehicles, or entering into rental agreements.