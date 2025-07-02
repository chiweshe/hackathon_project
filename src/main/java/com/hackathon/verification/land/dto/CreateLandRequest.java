package com.hackathon.verification.land.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Request DTO for creating a new land record.
 * Excludes auto-generated fields like id, createdAt, and updatedAt.
 */
public class CreateLandRequest {
    
    private String standNumber;
    private String location;
    private String title;
    private String ownerName;
    private String ownerIdNumber;
    private boolean isAllocated;
    private LocalDate allocationDate;
    private Double propertySizeSquareMeters;
    private String propertyType;
    private String verificationStatus;
    
    // Default constructor
    public CreateLandRequest() {
    }
    
    // Constructor with fields
    public CreateLandRequest(String standNumber, String location, String title, String ownerName, 
                  String ownerIdNumber, boolean isAllocated, LocalDate allocationDate,
                  Double propertySizeSquareMeters, String propertyType, String verificationStatus) {
        this.standNumber = standNumber;
        this.location = location;
        this.title = title;
        this.ownerName = ownerName;
        this.ownerIdNumber = ownerIdNumber;
        this.isAllocated = isAllocated;
        this.allocationDate = allocationDate;
        this.propertySizeSquareMeters = propertySizeSquareMeters;
        this.propertyType = propertyType;
        this.verificationStatus = verificationStatus;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getOwnerIdNumber() {
        return ownerIdNumber;
    }
    
    public void setOwnerIdNumber(String ownerIdNumber) {
        this.ownerIdNumber = ownerIdNumber;
    }
    
    public boolean isAllocated() {
        return isAllocated;
    }
    
    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }

    public LocalDate getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(LocalDate allocationDate) {
        this.allocationDate = allocationDate;
    }

    public Double getPropertySizeSquareMeters() {
        return propertySizeSquareMeters;
    }
    
    public void setPropertySizeSquareMeters(Double propertySizeSquareMeters) {
        this.propertySizeSquareMeters = propertySizeSquareMeters;
    }
    
    public String getPropertyType() {
        return propertyType;
    }
    
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    
    public String getVerificationStatus() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}