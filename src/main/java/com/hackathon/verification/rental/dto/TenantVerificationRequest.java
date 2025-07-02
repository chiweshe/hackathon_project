package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantVerificationRequest {
    
    @NotBlank(message = "At least one identifier (name, idNumber, phone, or address) must be provided")
    private String identifier;
    
    private String identifierType; // "NAME", "ID_NUMBER", "PHONE", "ADDRESS"
    
    private Boolean includeRentalHistory;
    
    private Boolean includeRatings;
    
    // Default constructor
    public TenantVerificationRequest() {
        this.includeRentalHistory = true;
        this.includeRatings = true;
    }
    
    // Constructor with fields
    public TenantVerificationRequest(String identifier, String identifierType, 
                                    Boolean includeRentalHistory, Boolean includeRatings) {
        this.identifier = identifier;
        this.identifierType = identifierType;
        this.includeRentalHistory = includeRentalHistory != null ? includeRentalHistory : true;
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
    
    public Boolean getIncludeRentalHistory() {
        return includeRentalHistory;
    }
    
    public void setIncludeRentalHistory(Boolean includeRentalHistory) {
        this.includeRentalHistory = includeRentalHistory;
    }
    
    public Boolean getIncludeRatings() {
        return includeRatings;
    }
    
    public void setIncludeRatings(Boolean includeRatings) {
        this.includeRatings = includeRatings;
    }
}