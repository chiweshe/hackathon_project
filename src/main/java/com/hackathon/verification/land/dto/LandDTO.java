package com.hackathon.verification.land.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LandDTO {

    private Long id;
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
    private LocalDate createdAt;
    private LocalDate updatedAt;
    
    // Default constructor
    public LandDTO() {
    }
    
    // Constructor with fields
    public LandDTO(Long id, String standNumber, String location, String title, String ownerName, 
                  String ownerIdNumber, boolean isAllocated, LocalDate allocationDate,
                  Double propertySizeSquareMeters, String propertyType, String verificationStatus) {
        this.id = id;
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
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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

    public void setAllocationDate(LocalDate allocationDate) {
        this.allocationDate = allocationDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}