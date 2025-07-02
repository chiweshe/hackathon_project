package com.hackathon.verification.land.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandVerificationResponse {
    
    private String standNumber;
    private String location;
    private boolean exists;
    private boolean isAllocated;
    private String ownerName;
    private String verificationStatus;
    private String message;
    private Double confidenceScore; // AI-generated confidence score
    
    // Default constructor
    public LandVerificationResponse() {
    }
    
    // Constructor with fields
    public LandVerificationResponse(String standNumber, String location, boolean exists, 
                                   boolean isAllocated, String ownerName, 
                                   String verificationStatus, String message) {
        this.standNumber = standNumber;
        this.location = location;
        this.exists = exists;
        this.isAllocated = isAllocated;
        this.ownerName = ownerName;
        this.verificationStatus = verificationStatus;
        this.message = message;
    }
    
    // Getters and Setters
    public String getStandNumber() {
        return standNumber;
    }
    
    public void setStandNumber(String standNumber) {
        this.standNumber = standNumber;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public boolean isExists() {
        return exists;
    }
    
    public void setExists(boolean exists) {
        this.exists = exists;
    }
    
    public boolean isAllocated() {
        return isAllocated;
    }
    
    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getVerificationStatus() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Double getConfidenceScore() {
        return confidenceScore;
    }
    
    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
}