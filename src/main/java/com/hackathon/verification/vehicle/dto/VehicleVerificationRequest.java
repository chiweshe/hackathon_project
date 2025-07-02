package com.hackathon.verification.vehicle.dto;

public class VehicleVerificationRequest {
    
    private String chassisNumber;
    private String registrationNumber;
    
    // Default constructor
    public VehicleVerificationRequest() {
    }
    
    // Constructor with fields
    public VehicleVerificationRequest(String chassisNumber, String registrationNumber) {
        this.chassisNumber = chassisNumber;
        this.registrationNumber = registrationNumber;
    }
    
    // Getters and Setters
    public String getChassisNumber() {
        return chassisNumber;
    }
    
    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}