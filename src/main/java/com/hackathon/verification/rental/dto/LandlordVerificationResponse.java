package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandlordVerificationResponse {
    
    private String name;
    private String idNumber;
    private String phone;
    private String address;
    private boolean exists;
    private String verificationStatus;
    private Double averageRating;
    private Integer trustScore; // 0-100 AI-generated trust score
    private String classification; // "Safe", "Caution", or "Avoid"
    private Integer responsivenessScore; // 0-100 score for responsiveness
    private Integer fairnessScore; // 0-100 score for fairness
    private Double depositReturnRate; // Percentage of deposits returned
    private String behavioralSummary; // AI-generated summary of behavior
    private List<String> redFlags; // List of red flags
    private List<String> managedProperties; // List of property addresses
    private List<PropertyDTO> properties; // Detailed property information
    private List<RatingDTO> ratings; // Ratings from tenants
    private String message;
    
    // Default constructor
    public LandlordVerificationResponse() {
        this.properties = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.redFlags = new ArrayList<>();
        this.managedProperties = new ArrayList<>();
    }
    
    // Constructor with essential fields
    public LandlordVerificationResponse(String name, String idNumber, String phone, 
                                       String address, boolean exists, 
                                       String verificationStatus, String message) {
        this.name = name;
        this.idNumber = idNumber;
        this.phone = phone;
        this.address = address;
        this.exists = exists;
        this.verificationStatus = verificationStatus;
        this.message = message;
        this.properties = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.redFlags = new ArrayList<>();
        this.managedProperties = new ArrayList<>();
    }
    
    // Inner class for property details
    public static class PropertyDTO {
        private String propertyAddress;
        private LocalDate managedSince;
        private Integer totalTenants;
        private Integer totalDisputes;
        private Integer totalEvictions;
        private Double averageRating;
        private List<String> tenantNames;
        
        public PropertyDTO() {
            this.tenantNames = new ArrayList<>();
        }
        
        // Getters and Setters
        public String getPropertyAddress() {
            return propertyAddress;
        }
        
        public void setPropertyAddress(String propertyAddress) {
            this.propertyAddress = propertyAddress;
        }
        
        public LocalDate getManagedSince() {
            return managedSince;
        }
        
        public void setManagedSince(LocalDate managedSince) {
            this.managedSince = managedSince;
        }
        
        public Integer getTotalTenants() {
            return totalTenants;
        }
        
        public void setTotalTenants(Integer totalTenants) {
            this.totalTenants = totalTenants;
        }
        
        public Integer getTotalDisputes() {
            return totalDisputes;
        }
        
        public void setTotalDisputes(Integer totalDisputes) {
            this.totalDisputes = totalDisputes;
        }
        
        public Integer getTotalEvictions() {
            return totalEvictions;
        }
        
        public void setTotalEvictions(Integer totalEvictions) {
            this.totalEvictions = totalEvictions;
        }
        
        public Double getAverageRating() {
            return averageRating;
        }
        
        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }
        
        public List<String> getTenantNames() {
            return tenantNames;
        }
        
        public void setTenantNames(List<String> tenantNames) {
            this.tenantNames = tenantNames;
        }
        
        public void addTenantName(String tenantName) {
            if (this.tenantNames == null) {
                this.tenantNames = new ArrayList<>();
            }
            this.tenantNames.add(tenantName);
        }
    }
    
    // Inner class for ratings
    public static class RatingDTO {
        private String tenantName;
        private Double ratingValue;
        private String review;
        private String propertyAddress;
        private LocalDate ratingDate;
        private Integer responsiveness;
        private Integer maintenanceQuality;
        private Integer fairness;
        private Integer depositHandling;
        private Integer privacyRespect;
        private List<String> detectedTraits;
        
        public RatingDTO() {
            this.detectedTraits = new ArrayList<>();
        }
        
        // Getters and Setters
        public String getTenantName() {
            return tenantName;
        }
        
        public void setTenantName(String tenantName) {
            this.tenantName = tenantName;
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
        
        public String getPropertyAddress() {
            return propertyAddress;
        }
        
        public void setPropertyAddress(String propertyAddress) {
            this.propertyAddress = propertyAddress;
        }
        
        public LocalDate getRatingDate() {
            return ratingDate;
        }
        
        public void setRatingDate(LocalDate ratingDate) {
            this.ratingDate = ratingDate;
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
        
        public List<String> getDetectedTraits() {
            return detectedTraits;
        }
        
        public void setDetectedTraits(List<String> detectedTraits) {
            this.detectedTraits = detectedTraits;
        }
        
        public void addDetectedTrait(String trait) {
            if (this.detectedTraits == null) {
                this.detectedTraits = new ArrayList<>();
            }
            this.detectedTraits.add(trait);
        }
    }
    
    // Getters and Setters for main class
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
    
    public boolean isExists() {
        return exists;
    }
    
    public void setExists(boolean exists) {
        this.exists = exists;
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
    
    public List<String> getRedFlags() {
        return redFlags;
    }
    
    public void setRedFlags(List<String> redFlags) {
        this.redFlags = redFlags;
    }
    
    public void addRedFlag(String redFlag) {
        if (this.redFlags == null) {
            this.redFlags = new ArrayList<>();
        }
        this.redFlags.add(redFlag);
    }
    
    public List<String> getManagedProperties() {
        return managedProperties;
    }
    
    public void setManagedProperties(List<String> managedProperties) {
        this.managedProperties = managedProperties;
    }
    
    public void addManagedProperty(String propertyAddress) {
        if (this.managedProperties == null) {
            this.managedProperties = new ArrayList<>();
        }
        this.managedProperties.add(propertyAddress);
    }
    
    public List<PropertyDTO> getProperties() {
        return properties;
    }
    
    public void setProperties(List<PropertyDTO> properties) {
        this.properties = properties;
    }
    
    public void addProperty(PropertyDTO property) {
        if (this.properties == null) {
            this.properties = new ArrayList<>();
        }
        this.properties.add(property);
    }
    
    public List<RatingDTO> getRatings() {
        return ratings;
    }
    
    public void setRatings(List<RatingDTO> ratings) {
        this.ratings = ratings;
    }
    
    public void addRating(RatingDTO rating) {
        if (this.ratings == null) {
            this.ratings = new ArrayList<>();
        }
        this.ratings.add(rating);
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}