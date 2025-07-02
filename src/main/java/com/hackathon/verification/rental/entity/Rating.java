package com.hackathon.verification.rental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "rating_value", nullable = false)
    private Double ratingValue;

    @Column(name = "review", columnDefinition = "TEXT")
    private String review;

    @Column(name = "rating_type", nullable = false)
    private String ratingType; // "LANDLORD_TO_TENANT" or "TENANT_TO_LANDLORD"

    @Column(name = "property_address")
    private String propertyAddress;

    @Column(name = "lease_start_date")
    private LocalDateTime leaseStartDate;

    @Column(name = "lease_end_date")
    private LocalDateTime leaseEndDate;

    // Tenant-specific rating metrics (used when rating_type is "LANDLORD_TO_TENANT")
    @Column(name = "payment_timeliness")
    private Integer paymentTimeliness; // 1-5 scale

    @Column(name = "property_care")
    private Integer propertyCare; // 1-5 scale

    @Column(name = "communication")
    private Integer communication; // 1-5 scale

    @Column(name = "rule_adherence")
    private Integer ruleAdherence; // 1-5 scale

    @Column(name = "cleanliness")
    private Integer cleanliness; // 1-5 scale

    // Landlord-specific rating metrics (used when rating_type is "TENANT_TO_LANDLORD")
    @Column(name = "responsiveness")
    private Integer responsiveness; // 1-5 scale

    @Column(name = "maintenance_quality")
    private Integer maintenanceQuality; // 1-5 scale

    @Column(name = "fairness")
    private Integer fairness; // 1-5 scale

    @Column(name = "deposit_handling")
    private Integer depositHandling; // 1-5 scale

    @Column(name = "privacy_respect")
    private Integer privacyRespect; // 1-5 scale

    @Column(name = "sentiment_score")
    private Double sentimentScore; // AI-generated sentiment analysis score

    @Column(name = "detected_traits", columnDefinition = "TEXT")
    private String detectedTraits; // Comma-separated list of traits detected by AI

    // Getters and setters for tenant-specific rating metrics
    public Integer getPaymentTimeliness() {
        return paymentTimeliness;
    }

    public void setPaymentTimeliness(Integer paymentTimeliness) {
        this.paymentTimeliness = paymentTimeliness;
    }

    public Integer getPropertyCare() {
        return propertyCare;
    }

    public void setPropertyCare(Integer propertyCare) {
        this.propertyCare = propertyCare;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public Integer getRuleAdherence() {
        return ruleAdherence;
    }

    public void setRuleAdherence(Integer ruleAdherence) {
        this.ruleAdherence = ruleAdherence;
    }

    public Integer getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(Integer cleanliness) {
        this.cleanliness = cleanliness;
    }

    // Getters and setters for landlord-specific rating metrics
    public Integer getResponsiveness() {
        return responsiveness;
    }

    public void setResponsiveness(Integer responsiveness) {
        this.responsiveness = responsiveness;
    }

    public Integer getMaintenanceQuality() {
        return maintenanceQuality;
    }

    public void setMaintenanceQuality(Integer maintenanceQuality) {
        this.maintenanceQuality = maintenanceQuality;
    }

    public Integer getFairness() {
        return fairness;
    }

    public void setFairness(Integer fairness) {
        this.fairness = fairness;
    }

    public Integer getDepositHandling() {
        return depositHandling;
    }

    public void setDepositHandling(Integer depositHandling) {
        this.depositHandling = depositHandling;
    }

    public Integer getPrivacyRespect() {
        return privacyRespect;
    }

    public void setPrivacyRespect(Integer privacyRespect) {
        this.privacyRespect = privacyRespect;
    }

    public String getDetectedTraits() {
        return detectedTraits;
    }

    public void setDetectedTraits(String detectedTraits) {
        this.detectedTraits = detectedTraits;
    }

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Default constructor
    public Rating() {
    }

    // Constructor with fields
    public Rating(Landlord landlord, Tenant tenant, Double ratingValue, String review, 
                 String ratingType, String propertyAddress, 
                 LocalDateTime leaseStartDate, LocalDateTime leaseEndDate) {
        this.landlord = landlord;
        this.tenant = tenant;
        this.ratingValue = ratingValue;
        this.review = review;
        this.ratingType = ratingType;
        this.propertyAddress = propertyAddress;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public LocalDateTime getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDateTime leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDateTime getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDateTime leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
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

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", landlord=" + (landlord != null ? landlord.getId() : null) +
                ", tenant=" + (tenant != null ? tenant.getId() : null) +
                ", ratingValue=" + ratingValue +
                ", ratingType='" + ratingType + '\'' +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", sentimentScore=" + sentimentScore +
                '}';
    }
}
