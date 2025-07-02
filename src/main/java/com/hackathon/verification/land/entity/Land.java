package com.hackathon.verification.land.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lands")
public class Land {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String standNumber;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String title;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_id_number")
    private String ownerIdNumber;

    @Column(name = "is_allocated")
    private boolean isAllocated;

    @Column(name = "allocation_date")
    private LocalDate allocationDate;

    @Column(name = "property_size_sqm")
    private Double propertySizeSquareMeters;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "verification_status")
    private String verificationStatus;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    // Default constructor
    public Land() {
    }

    // Constructor with fields
    public Land(String standNumber, String location, String title, String ownerName, 
                String ownerIdNumber, boolean isAllocated, String propertyType) {
        this.standNumber = standNumber;
        this.location = location;
        this.title = title;
        this.ownerName = ownerName;
        this.ownerIdNumber = ownerIdNumber;
        this.isAllocated = isAllocated;
        this.propertyType = propertyType;
        this.verificationStatus = "PENDING";
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

    public void setAllocationDate(LocalDate allocationDate) {
        this.allocationDate = allocationDate;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Land{" +
                "id=" + id +
                ", standNumber='" + standNumber + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", isAllocated=" + isAllocated +
                ", propertyType='" + propertyType + '\'' +
                ", verificationStatus='" + verificationStatus + '\'' +
                '}';
    }
}
