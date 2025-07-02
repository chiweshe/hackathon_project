package com.hackathon.verification.vehicle.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleVerificationResponse {
    
    private String chassisNumber;
    private String registrationNumber;
    private boolean exists;
    private String make;
    private String model;
    private Integer year;
    private String currentOwnerName;
    private LocalDate purchaseDate;
    private Boolean isStolen;
    private Boolean hasBeenTampered;
    private String verificationStatus;
    private String message;
    private Double confidenceScore; // AI-generated confidence score
    private List<OwnershipHistory> ownershipHistory; // List of previous owners
    
    // Default constructor
    public VehicleVerificationResponse() {
        this.ownershipHistory = new ArrayList<>();
    }
    
    // Constructor with fields
    public VehicleVerificationResponse(String chassisNumber, String registrationNumber, boolean exists, 
                                      String make, String model, Integer year, String currentOwnerName, 
                                      Boolean isStolen, Boolean hasBeenTampered, 
                                      String verificationStatus, String message) {
        this.chassisNumber = chassisNumber;
        this.registrationNumber = registrationNumber;
        this.exists = exists;
        this.make = make;
        this.model = model;
        this.year = year;
        this.currentOwnerName = currentOwnerName;
        this.isStolen = isStolen;
        this.hasBeenTampered = hasBeenTampered;
        this.verificationStatus = verificationStatus;
        this.message = message;
        this.ownershipHistory = new ArrayList<>();
    }
    
    // Inner class for ownership history
    public static class OwnershipHistory {
        private String ownerName;
        private String ownerId;
        private LocalDate startDate;
        private LocalDate endDate;
        
        public OwnershipHistory() {
        }
        
        public OwnershipHistory(String ownerName, String ownerId, LocalDate startDate, LocalDate endDate) {
            this.ownerName = ownerName;
            this.ownerId = ownerId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
        
        // Getters and Setters
        public String getOwnerName() {
            return ownerName;
        }
        
        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }
        
        public String getOwnerId() {
            return ownerId;
        }
        
        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
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
    
    public boolean isExists() {
        return exists;
    }
    
    public void setExists(boolean exists) {
        this.exists = exists;
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
    
    public String getCurrentOwnerName() {
        return currentOwnerName;
    }
    
    public void setCurrentOwnerName(String currentOwnerName) {
        this.currentOwnerName = currentOwnerName;
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
    
    public List<OwnershipHistory> getOwnershipHistory() {
        return ownershipHistory;
    }
    
    public void setOwnershipHistory(List<OwnershipHistory> ownershipHistory) {
        this.ownershipHistory = ownershipHistory;
    }
    
    // Helper method to add an ownership history entry
    public void addOwnershipHistory(OwnershipHistory history) {
        if (this.ownershipHistory == null) {
            this.ownershipHistory = new ArrayList<>();
        }
        this.ownershipHistory.add(history);
    }
}