package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantVerificationResponse {
    
    private String name;
    private String idNumber;
    private String phone;
    private String currentAddress;
    private boolean exists;
    private String verificationStatus;
    private Double averageRating;
    private Integer trustScore; // 0-100 AI-generated trust score
    private String classification; // "Safe", "Caution", or "Avoid"
    private String behavioralSummary; // AI-generated summary of behavior
    private List<String> redFlags; // List of red flags
    private List<RentalHistoryDTO> rentalHistory;
    private List<RatingDTO> ratings;
    private String message;
    
    // Default constructor
    public TenantVerificationResponse() {
        this.rentalHistory = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.redFlags = new ArrayList<>();
    }
    
    // Constructor with essential fields
    public TenantVerificationResponse(String name, String idNumber, String phone, 
                                     String currentAddress, boolean exists, 
                                     String verificationStatus, String message) {
        this.name = name;
        this.idNumber = idNumber;
        this.phone = phone;
        this.currentAddress = currentAddress;
        this.exists = exists;
        this.verificationStatus = verificationStatus;
        this.message = message;
        this.rentalHistory = new ArrayList<>();
        this.ratings = new ArrayList<>();
        this.redFlags = new ArrayList<>();
    }
    
    // Inner class for rental history
    public static class RentalHistoryDTO {
        private String propertyAddress;
        private LocalDate leaseStartDate;
        private LocalDate leaseEndDate;
        private Double rentAmount;
        private Boolean onTimePayments;
        private Integer latePaymentsCount;
        private Boolean propertyDamage;
        private String damageDescription;
        private Boolean hadDisputes;
        private String disputeDescription;
        private Boolean evictionFiled;
        private String evictionReason;
        private String landlordName;
        
        public RentalHistoryDTO() {
        }
        
        // Getters and Setters
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
        
        public Double getRentAmount() {
            return rentAmount;
        }
        
        public void setRentAmount(Double rentAmount) {
            this.rentAmount = rentAmount;
        }
        
        public Boolean getOnTimePayments() {
            return onTimePayments;
        }
        
        public void setOnTimePayments(Boolean onTimePayments) {
            this.onTimePayments = onTimePayments;
        }
        
        public Integer getLatePaymentsCount() {
            return latePaymentsCount;
        }
        
        public void setLatePaymentsCount(Integer latePaymentsCount) {
            this.latePaymentsCount = latePaymentsCount;
        }
        
        public Boolean getPropertyDamage() {
            return propertyDamage;
        }
        
        public void setPropertyDamage(Boolean propertyDamage) {
            this.propertyDamage = propertyDamage;
        }
        
        public String getDamageDescription() {
            return damageDescription;
        }
        
        public void setDamageDescription(String damageDescription) {
            this.damageDescription = damageDescription;
        }
        
        public Boolean getHadDisputes() {
            return hadDisputes;
        }
        
        public void setHadDisputes(Boolean hadDisputes) {
            this.hadDisputes = hadDisputes;
        }
        
        public String getDisputeDescription() {
            return disputeDescription;
        }
        
        public void setDisputeDescription(String disputeDescription) {
            this.disputeDescription = disputeDescription;
        }
        
        public Boolean getEvictionFiled() {
            return evictionFiled;
        }
        
        public void setEvictionFiled(Boolean evictionFiled) {
            this.evictionFiled = evictionFiled;
        }
        
        public String getEvictionReason() {
            return evictionReason;
        }
        
        public void setEvictionReason(String evictionReason) {
            this.evictionReason = evictionReason;
        }
        
        public String getLandlordName() {
            return landlordName;
        }
        
        public void setLandlordName(String landlordName) {
            this.landlordName = landlordName;
        }
    }
    
    // Inner class for ratings
    public static class RatingDTO {
        private String landlordName;
        private Double ratingValue;
        private String review;
        private String propertyAddress;
        private LocalDate ratingDate;
        private Integer paymentTimeliness;
        private Integer propertyCare;
        private Integer communication;
        private Integer ruleAdherence;
        private Integer cleanliness;
        private List<String> detectedTraits;
        
        public RatingDTO() {
            this.detectedTraits = new ArrayList<>();
        }
        
        // Getters and Setters
        public String getLandlordName() {
            return landlordName;
        }
        
        public void setLandlordName(String landlordName) {
            this.landlordName = landlordName;
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
    
    public String getCurrentAddress() {
        return currentAddress;
    }
    
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
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
    
    public List<RentalHistoryDTO> getRentalHistory() {
        return rentalHistory;
    }
    
    public void setRentalHistory(List<RentalHistoryDTO> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }
    
    public void addRentalHistory(RentalHistoryDTO history) {
        if (this.rentalHistory == null) {
            this.rentalHistory = new ArrayList<>();
        }
        this.rentalHistory.add(history);
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