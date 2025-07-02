package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingDTO {
    
    @NotNull(message = "Landlord ID is required")
    private Long landlordId;
    
    @NotNull(message = "Tenant ID is required")
    private Long tenantId;
    
    @NotNull(message = "Rating value is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Double ratingValue;
    
    private String review;
    
    @NotBlank(message = "Rating type is required")
    private String ratingType; // "LANDLORD_TO_TENANT" or "TENANT_TO_LANDLORD"
    
    @NotBlank(message = "Property address is required")
    private String propertyAddress;
    
    private LocalDate leaseStartDate;
    
    private LocalDate leaseEndDate;
    
    // Tenant-specific rating metrics (used when ratingType is "LANDLORD_TO_TENANT")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer paymentTimeliness; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer propertyCare; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer communication; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer ruleAdherence; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer cleanliness; // 1-5 scale
    
    // Landlord-specific rating metrics (used when ratingType is "TENANT_TO_LANDLORD")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer responsiveness; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer maintenanceQuality; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer fairness; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer depositHandling; // 1-5 scale
    
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer privacyRespect; // 1-5 scale
    
    // Default constructor
    public RatingDTO() {
    }
    
    // Constructor with essential fields
    public RatingDTO(Long landlordId, Long tenantId, Double ratingValue, String review, 
                    String ratingType, String propertyAddress, 
                    LocalDate leaseStartDate, LocalDate leaseEndDate) {
        this.landlordId = landlordId;
        this.tenantId = tenantId;
        this.ratingValue = ratingValue;
        this.review = review;
        this.ratingType = ratingType;
        this.propertyAddress = propertyAddress;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
    }
    
    // Getters and Setters
    public Long getLandlordId() {
        return landlordId;
    }
    
    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }
    
    public Long getTenantId() {
        return tenantId;
    }
    
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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
    
    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }
    
    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }
    
    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }
    
    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }
    
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
}