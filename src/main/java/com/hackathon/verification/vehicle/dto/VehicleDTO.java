package com.hackathon.verification.vehicle.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VehicleDTO {
    
    private Long id;
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
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Default constructor
    public VehicleDTO() {
    }
    
    // Constructor with fields
    public VehicleDTO(Long id, String chassisNumber, String registrationNumber, String make, String model, 
                     Integer year, String color, String engineNumber, String currentOwnerName, 
                     String currentOwnerId, Boolean isStolen, Boolean hasBeenTampered, 
                     String verificationStatus) {
        this.id = id;
        this.chassisNumber = chassisNumber;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.engineNumber = engineNumber;
        this.currentOwnerName = currentOwnerName;
        this.currentOwnerId = currentOwnerId;
        this.isStolen = isStolen;
        this.hasBeenTampered = hasBeenTampered;
        this.verificationStatus = verificationStatus;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}