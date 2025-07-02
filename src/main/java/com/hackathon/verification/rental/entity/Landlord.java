package com.hackathon.verification.rental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "landlords")
public class Landlord {

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

    @Column(name = "address")
    private String address;

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

    @Column(name = "responsiveness_score")
    private Integer responsivenessScore; // 0-100 score for responsiveness to tenant issues

    @Column(name = "fairness_score")
    private Integer fairnessScore; // 0-100 score for fairness in agreements and rent increases

    @Column(name = "deposit_return_rate")
    private Double depositReturnRate; // Percentage of security deposits returned in full

    @Column(name = "behavioral_summary", columnDefinition = "TEXT")
    private String behavioralSummary; // AI-generated summary of behavior

    @Column(name = "red_flags", columnDefinition = "TEXT")
    private String redFlags; // Comma-separated list of red flags

    @Column(name = "managed_properties", columnDefinition = "TEXT")
    private String managedProperties; // Comma-separated list of property addresses

    @OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalHistory> rentalHistories = new ArrayList<>();

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
    public Landlord() {
    }

    // Constructor with fields
    public Landlord(String name, String idNumber, String email, String phone, String address) {
        this.name = name;
        this.idNumber = idNumber;
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    // Additional getters and setters for AI-generated assessments
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

    public Integer getResponsivenessScore() {
        return responsivenessScore;
    }

    public void setResponsivenessScore(Integer responsivenessScore) {
        this.responsivenessScore = responsivenessScore;
    }

    public Integer getFairnessScore() {
        return fairnessScore;
    }

    public void setFairnessScore(Integer fairnessScore) {
        this.fairnessScore = fairnessScore;
    }

    public Double getDepositReturnRate() {
        return depositReturnRate;
    }

    public void setDepositReturnRate(Double depositReturnRate) {
        this.depositReturnRate = depositReturnRate;
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

    public String getManagedProperties() {
        return managedProperties;
    }

    public void setManagedProperties(String managedProperties) {
        this.managedProperties = managedProperties;
    }

    @Override
    public String toString() {
        return "Landlord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", verificationStatus='" + verificationStatus + '\'' +
                ", averageRating=" + averageRating +
                ", totalRatings=" + totalRatings +
                '}';
    }
}
