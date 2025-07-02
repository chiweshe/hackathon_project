package com.hackathon.verification.land.dto;

public class LandVerificationRequest {
    
    private String standNumber;
    private String location;
    
    // Default constructor
    public LandVerificationRequest() {
    }
    
    // Constructor with fields
    public LandVerificationRequest(String standNumber, String location) {
        this.standNumber = standNumber;
        this.location = location;
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
}