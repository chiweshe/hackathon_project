package com.hackathon.verification.vehicle.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chassis_number", nullable = false, unique = true)
    private String chassisNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Integer year;

    @Column(name = "color")
    private String color;

    @Column(name = "engine_number")
    private String engineNumber;

    @Column(name = "current_owner_name")
    private String currentOwnerName;

    @Column(name = "current_owner_id")
    private String currentOwnerId;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "is_stolen")
    private Boolean isStolen;

    @Column(name = "has_been_tampered")
    private Boolean hasBeenTampered;

    @Column(name = "verification_status")
    private String verificationStatus;

    @Column(name = "verification_notes", columnDefinition = "TEXT")
    private String verificationNotes;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
        if (isStolen == null) isStolen = false;
        if (hasBeenTampered == null) hasBeenTampered = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    // Default constructor
    public Vehicle() {
    }

    // Constructor with fields
    public Vehicle(String chassisNumber, String registrationNumber, String make, String model, 
                  Integer year, String color, String engineNumber, String currentOwnerName, 
                  String currentOwnerId) {
        this.chassisNumber = chassisNumber;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.engineNumber = engineNumber;
        this.currentOwnerName = currentOwnerName;
        this.currentOwnerId = currentOwnerId;
        this.isStolen = false;
        this.hasBeenTampered = false;
        this.verificationStatus = "PENDING";
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", currentOwnerName='" + currentOwnerName + '\'' +
                ", isStolen=" + isStolen +
                ", hasBeenTampered=" + hasBeenTampered +
                ", verificationStatus='" + verificationStatus + '\'' +
                '}';
    }
}