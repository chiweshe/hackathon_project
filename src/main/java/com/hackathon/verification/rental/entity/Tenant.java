package com.hackathon.verification.rental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "employer")
    private String employer;

    @Column(name = "monthly_income")
    private Double monthlyIncome;

    @Column(name = "verification_status")
    private String verificationStatus;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_ratings")
    private Integer totalRatings;

    @Column(name = "trust_score")
    private Integer trustScore; // 0-100 AI-generated trust score

    @Column(name = "classification")
    private String classification; // "Safe", "Caution", or "Avoid"

    @Column(name = "behavioral_summary", columnDefinition = "TEXT")
    private String behavioralSummary; // AI-generated summary of behavior

    @Column(name = "red_flags", columnDefinition = "TEXT")
    private String redFlags; // Comma-separated list of red flags

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalHistory> rentalHistories = new ArrayList<>();

    // Getters and setters for AI-generated assessments
    public Integer getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Integer trustScore) {
        this.trustScore = trustScore;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getBehavioralSummary() {
        return behavioralSummary;
    }

    public void setBehavioralSummary(String behavioralSummary) {
        this.behavioralSummary = behavioralSummary;
    }

    public String getRedFlags() {
        return redFlags;
    }

    public void setRedFlags(String redFlags) {
        this.redFlags = redFlags;
    }

    public List<RentalHistory> getRentalHistories() {
        return rentalHistories;
    }

    public void setRentalHistories(List<RentalHistory> rentalHistories) {
        this.rentalHistories = rentalHistories;
    }

    public void addRentalHistory(RentalHistory rentalHistory) {
        rentalHistories.add(rentalHistory);
        rentalHistory.setTenant(this);
    }

    public void removeRentalHistory(RentalHistory rentalHistory) {
        rentalHistories.remove(rentalHistory);
        rentalHistory.setTenant(null);
    }

    @Column(name = "rental_history", columnDefinition = "TEXT")
    private String rentalHistory; // Legacy field, to be replaced by rentalHistories

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (averageRating == null) averageRating = 0.0;
        if (totalRatings == null) totalRatings = 0;
        if (verificationStatus == null) verificationStatus = "PENDING";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Default constructor
    public Tenant() {
    }

    // Constructor with fields
    public Tenant(String name, String idNumber, String email, String phone, 
                 String currentAddress, String employmentStatus, String employer, Double monthlyIncome) {
        this.name = name;
        this.idNumber = idNumber;
        this.email = email;
        this.phone = phone;
        this.currentAddress = currentAddress;
        this.employmentStatus = employmentStatus;
        this.employer = employer;
        this.monthlyIncome = monthlyIncome;
        this.verificationStatus = "PENDING";
        this.averageRating = 0.0;
        this.totalRatings = 0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(String rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Method to update rating when a new rating is added
    public void addRating(Double rating) {
        if (this.totalRatings == 0) {
            this.averageRating = rating;
        } else {
            double totalScore = this.averageRating * this.totalRatings;
            totalScore += rating;
            this.averageRating = totalScore / (this.totalRatings + 1);
        }
        this.totalRatings++;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", verificationStatus='" + verificationStatus + '\'' +
                ", averageRating=" + averageRating +
                ", totalRatings=" + totalRatings +
                '}';
    }
}
