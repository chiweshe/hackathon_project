package com.hackathon.verification.vehicle.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Request DTO for creating a new vehicle record.
 * Excludes auto-generated fields like id, createdAt, and updatedAt.
 */
public class CreateVehicleRequest {
    
    private String chassisNumber;
    private String registrationNumber;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private String engineNumber;
    private String currentOwnerName;
    private String currentOwnerId;
    private LocalDate purchaseDate;
    private Boolean isStolen;
    private Boolean hasBeenTampered;
    private String verificationStatus;
    private String verificationNotes;
    
    // Default constructor
    public CreateVehicleRequest() {
    }
    
    // Constructor with fields
    public CreateVehicleRequest(String chassisNumber, String registrationNumber, String make, String model, 
                     Integer year, String color, String engineNumber, String currentOwnerName, 
                     String currentOwnerId, LocalDate purchaseDate, Boolean isStolen,
                     Boolean hasBeenTampered, String verificationStatus, String verificationNotes) {
        this.chassisNumber = chassisNumber;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.engineNumber = engineNumber;
        this.currentOwnerName = currentOwnerName;
        this.currentOwnerId = currentOwnerId;
        this.purchaseDate = purchaseDate;
        this.isStolen = isStolen;
        this.hasBeenTampered = hasBeenTampered;
        this.verificationStatus = verificationStatus;
        this.verificationNotes = verificationNotes;
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
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getEngineNumber() {
        return engineNumber;
    }
    
    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }
    
    public String getCurrentOwnerName() {
        return currentOwnerName;
    }
    
    public void setCurrentOwnerName(String currentOwnerName) {
        this.currentOwnerName = currentOwnerName;
    }
    
    public String getCurrentOwnerId() {
        return currentOwnerId;
    }
    
    public void setCurrentOwnerId(String currentOwnerId) {
        this.currentOwnerId = currentOwnerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Boolean getStolen() {
        return isStolen;
    }

    public void setStolen(Boolean stolen) {
        isStolen = stolen;
    }

    public Boolean getIsStolen() {
        return isStolen;
    }
    
    public void setIsStolen(Boolean isStolen) {
        this.isStolen = isStolen;
    }
    
    public Boolean getHasBeenTampered() {
        return hasBeenTampered;
    }
    
    public void setHasBeenTampered(Boolean hasBeenTampered) {
        this.hasBeenTampered = hasBeenTampered;
    }
    
    public String getVerificationStatus() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
    
    public String getVerificationNotes() {
        return verificationNotes;
    }
    
    public void setVerificationNotes(String verificationNotes) {
        this.verificationNotes = verificationNotes;
    }
}