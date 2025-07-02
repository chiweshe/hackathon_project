package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandlordVerificationRequest {
    
    @NotBlank(message = "At least one identifier (name, idNumber, phone, or address) must be provided")
    private String identifier;
    
    private String identifierType; // "NAME", "ID_NUMBER", "PHONE", "ADDRESS", "PROPERTY_ADDRESS"
    
    private Boolean includeProperties;
    
    private Boolean includeRatings;
    
    // Default constructor
    public LandlordVerificationRequest() {
        this.includeProperties = true;
        this.includeRatings = true;
    }
    
    // Constructor with fields
    public LandlordVerificationRequest(String identifier, String identifierType, 
                                      Boolean includeProperties, Boolean includeRatings) {
        this.identifier = identifier;
        this.identifierType = identifierType;
        this.includeProperties = includeProperties != null ? includeProperties : true;
        this.includeRatings = includeRatings != null ? includeRatings : true;
    }
    
    // Getters and Setters
    public String getIdentifier() {
        return identifier;
    }
    
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifierType() {
        return identifierType;
    }
    
    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
    
    public Boolean getIncludeProperties() {
        return includeProperties;
    }
    
    public void setIncludeProperties(Boolean includeProperties) {
        this.includeProperties = includeProperties;
    }
    
    public Boolean getIncludeRatings() {
        return includeRatings;
    }
    
    public void setIncludeRatings(Boolean includeRatings) {
        this.includeRatings = includeRatings;
    }
}