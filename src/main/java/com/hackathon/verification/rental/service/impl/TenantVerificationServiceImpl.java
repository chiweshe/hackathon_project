package com.hackathon.verification.rental.service.impl;

import com.hackathon.verification.rental.dto.TenantVerificationRequest;
import com.hackathon.verification.rental.dto.TenantVerificationResponse;
import com.hackathon.verification.rental.entity.Rating;
import com.hackathon.verification.rental.entity.RentalHistory;
import com.hackathon.verification.rental.entity.Tenant;
import com.hackathon.verification.rental.repository.RatingRepository;
import com.hackathon.verification.rental.repository.RentalHistoryRepository;
import com.hackathon.verification.rental.repository.TenantRepository;
import com.hackathon.verification.rental.service.TenantVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TenantVerificationServiceImpl implements TenantVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(TenantVerificationServiceImpl.class);
    
    @Autowired
    private TenantRepository tenantRepository;
    
    @Autowired
    private RentalHistoryRepository rentalHistoryRepository;
    
    @Autowired
    private RatingRepository ratingRepository;
    
    @Override
    public TenantVerificationResponse verifyTenant(TenantVerificationRequest request) {
        logger.info("Verifying tenant with identifier: {}, type: {}", 
                    request.getIdentifier(), request.getIdentifierType());
        
        // Find tenant by identifier
        Tenant tenant = findTenantByIdentifier(request.getIdentifier(), request.getIdentifierType());
        
        // If tenant not found, return not found response
        if (tenant == null) {
            return createNotFoundResponse(request.getIdentifier());
        }
        
        // Calculate trust score and determine classification
        Integer trustScore = calculateTrustScore(tenant);
        String classification = determineClassification(tenant, trustScore);
        
        // Update tenant with new trust score and classification
        tenant = updateTenantTrustScoreAndClassification(tenant, trustScore, classification);
        
        // Generate behavioral summary and identify red flags
        String behavioralSummary = generateBehavioralSummary(tenant);
        List<String> redFlags = identifyRedFlags(tenant);
        
        // Create response with tenant details
        TenantVerificationResponse response = new TenantVerificationResponse(
            tenant.getName(),
            tenant.getIdNumber(),
            tenant.getPhone(),
            tenant.getCurrentAddress(),
            true,
            tenant.getVerificationStatus(),
            "Tenant verification completed successfully"
        );
        
        // Set AI-generated assessments
        response.setAverageRating(tenant.getAverageRating());
        response.setTrustScore(trustScore);
        response.setClassification(classification);
        response.setBehavioralSummary(behavioralSummary);
        response.setRedFlags(redFlags);
        
        // Add rental history if requested
        if (Boolean.TRUE.equals(request.getIncludeRentalHistory())) {
            addRentalHistoryToResponse(tenant, response);
        }
        
        // Add ratings if requested
        if (Boolean.TRUE.equals(request.getIncludeRatings())) {
            addRatingsToResponse(tenant, response);
        }
        
        return response;
    }
    
    @Override
    public Tenant findTenantByIdentifier(String identifier, String identifierType) {
        if (identifier == null || identifier.trim().isEmpty()) {
            return null;
        }
        
        if (identifierType == null) {
            identifierType = "ID_NUMBER"; // Default to ID number if not specified
        }
        
        switch (identifierType.toUpperCase()) {
            case "NAME":
                List<Tenant> tenantsByName = tenantRepository.findByNameContainingIgnoreCase(identifier);
                return tenantsByName.isEmpty() ? null : tenantsByName.get(0);
            case "ID_NUMBER":
                return tenantRepository.findByIdNumber(identifier);
            case "PHONE":
                return tenantRepository.findByPhone(identifier);
            case "ADDRESS":
                List<Tenant> tenantsByAddress = tenantRepository.findByCurrentAddressContainingIgnoreCase(identifier);
                return tenantsByAddress.isEmpty() ? null : tenantsByAddress.get(0);
            default:
                logger.warn("Unsupported identifier type: {}", identifierType);
                return null;
        }
    }
    
    @Override
    public Integer calculateTrustScore(Tenant tenant) {
        if (tenant == null) {
            return 0;
        }
        
        // Get rental histories for this tenant
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByTenant(tenant);
        
        // Get ratings for this tenant
        List<Rating> ratings = ratingRepository.findByTenantAndRatingType(tenant, "LANDLORD_TO_TENANT");
        
        // Base score starts at 70 (neutral)
        int score = 70;
        
        // Adjust score based on rental history
        if (!rentalHistories.isEmpty()) {
            // Count late payments, property damage, disputes, and evictions
            int latePaymentsCount = rentalHistories.stream()
                    .mapToInt(rh -> rh.getLatePaymentsCount() != null ? rh.getLatePaymentsCount() : 0)
                    .sum();
            
            int propertyDamageCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getPropertyDamage()))
                    .count();
            
            int disputesCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                    .count();
            
            int evictionsCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                    .count();
            
            // Deduct points for negative factors
            score -= latePaymentsCount * 3; // -3 points per late payment
            score -= propertyDamageCount * 10; // -10 points per property damage
            score -= disputesCount * 5; // -5 points per dispute
            score -= evictionsCount * 20; // -20 points per eviction
            
            // Add points for positive factors (on-time payments)
            int onTimePaymentsCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getOnTimePayments()))
                    .count();
            
            score += onTimePaymentsCount * 2; // +2 points per on-time payment record
        }
        
        // Adjust score based on ratings
        if (!ratings.isEmpty()) {
            // Calculate average rating value
            double avgRating = ratings.stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(3.0); // Default to neutral (3 out of 5)
            
            // Adjust score based on average rating (can contribute -20 to +20 points)
            score += (avgRating - 3) * 10; // -20 to +20 points
            
            // Consider sentiment scores if available
            double avgSentiment = ratings.stream()
                    .filter(r -> r.getSentimentScore() != null)
                    .mapToDouble(Rating::getSentimentScore)
                    .average()
                    .orElse(0.0);
            
            // Sentiment ranges from -1 to 1, adjust score by up to ±10 points
            score += avgSentiment * 10;
        }
        
        // Ensure score is within 0-100 range
        return Math.max(0, Math.min(100, score));
    }
    
    @Override
    public String determineClassification(Tenant tenant, Integer trustScore) {
        if (tenant == null || trustScore == null) {
            return "Unknown";
        }
        
        // Get rental histories for this tenant
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByTenant(tenant);
        
        // Check for automatic "Avoid" conditions
        boolean hasEviction = rentalHistories.stream()
                .anyMatch(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()));
        
        boolean hasMultiplePropertyDamage = rentalHistories.stream()
                .filter(rh -> Boolean.TRUE.equals(rh.getPropertyDamage()))
                .count() >= 2;
        
        if (hasEviction || hasMultiplePropertyDamage || trustScore < 40) {
            return "Avoid";
        }
        
        // Check for "Caution" conditions
        boolean hasDispute = rentalHistories.stream()
                .anyMatch(rh -> Boolean.TRUE.equals(rh.getHadDisputes()));
        
        boolean hasPropertyDamage = rentalHistories.stream()
                .anyMatch(rh -> Boolean.TRUE.equals(rh.getPropertyDamage()));
        
        boolean hasMultipleLatePayments = rentalHistories.stream()
                .mapToInt(rh -> rh.getLatePaymentsCount() != null ? rh.getLatePaymentsCount() : 0)
                .sum() >= 3;
        
        if (hasDispute || hasPropertyDamage || hasMultipleLatePayments || trustScore < 70) {
            return "Caution";
        }
        
        // Default to "Safe"
        return "Safe";
    }
    
    @Override
    public String generateBehavioralSummary(Tenant tenant) {
        if (tenant == null) {
            return "No tenant information available.";
        }
        
        // Get rental histories for this tenant
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByTenant(tenant);
        
        // Get ratings for this tenant
        List<Rating> ratings = ratingRepository.findByTenantAndRatingType(tenant, "LANDLORD_TO_TENANT");
        
        // Build behavioral summary
        StringBuilder summary = new StringBuilder();
        
        // Add rental history insights
        if (!rentalHistories.isEmpty()) {
            int totalRentals = rentalHistories.size();
            int latePaymentsCount = rentalHistories.stream()
                    .mapToInt(rh -> rh.getLatePaymentsCount() != null ? rh.getLatePaymentsCount() : 0)
                    .sum();
            
            int propertyDamageCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getPropertyDamage()))
                    .count();
            
            int disputesCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                    .count();
            
            int evictionsCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                    .count();
            
            summary.append("Tenant has ").append(totalRentals).append(" rental records. ");
            
            if (latePaymentsCount > 0) {
                summary.append("Has ").append(latePaymentsCount).append(" late payment(s). ");
            } else {
                summary.append("Has consistent on-time payment history. ");
            }
            
            if (propertyDamageCount > 0) {
                summary.append("Has caused property damage ").append(propertyDamageCount).append(" time(s). ");
            } else {
                summary.append("Has maintained properties well. ");
            }
            
            if (disputesCount > 0) {
                summary.append("Has been involved in ").append(disputesCount).append(" dispute(s). ");
            }
            
            if (evictionsCount > 0) {
                summary.append("Has ").append(evictionsCount).append(" eviction(s) on record. ");
            }
        } else {
            summary.append("No rental history records available. ");
        }
        
        // Add rating insights
        if (!ratings.isEmpty()) {
            double avgRating = ratings.stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(0);
            
            summary.append("Average rating from landlords is ").append(String.format("%.1f", avgRating)).append("/5. ");
            
            // Extract traits from reviews if available
            List<String> detectedTraits = ratings.stream()
                    .filter(r -> r.getDetectedTraits() != null && !r.getDetectedTraits().isEmpty())
                    .map(Rating::getDetectedTraits)
                    .flatMap(traits -> Arrays.stream(traits.split(",")))
                    .map(String::trim)
                    .distinct()
                    .collect(Collectors.toList());
            
            if (!detectedTraits.isEmpty()) {
                summary.append("Commonly mentioned traits: ").append(String.join(", ", detectedTraits)).append(".");
            }
        } else {
            summary.append("No rating information available.");
        }
        
        return summary.toString();
    }
    
    @Override
    public List<String> identifyRedFlags(Tenant tenant) {
        List<String> redFlags = new ArrayList<>();
        
        if (tenant == null) {
            return redFlags;
        }
        
        // Get rental histories for this tenant
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByTenant(tenant);
        
        // Check for red flags in rental history
        if (!rentalHistories.isEmpty()) {
            // Check for evictions
            long evictionsCount = rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                    .count();
            
            if (evictionsCount > 0) {
                redFlags.add("Has " + evictionsCount + " eviction(s) on record");
            }
            
            // Check for property damage
            long propertyDamageCount = rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getPropertyDamage()))
                    .count();
            
            if (propertyDamageCount > 0) {
                redFlags.add("Has caused property damage " + propertyDamageCount + " time(s)");
            }
            
            // Check for disputes
            long disputesCount = rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                    .count();
            
            if (disputesCount > 0) {
                redFlags.add("Has been involved in " + disputesCount + " dispute(s)");
            }
            
            // Check for late payments
            int latePaymentsCount = rentalHistories.stream()
                    .mapToInt(rh -> rh.getLatePaymentsCount() != null ? rh.getLatePaymentsCount() : 0)
                    .sum();
            
            if (latePaymentsCount >= 3) {
                redFlags.add("Has " + latePaymentsCount + " late payment(s)");
            }
        }
        
        // Get ratings for this tenant
        List<Rating> ratings = ratingRepository.findByTenantAndRatingType(tenant, "LANDLORD_TO_TENANT");
        
        // Check for red flags in ratings
        if (!ratings.isEmpty()) {
            // Check for low average rating
            double avgRating = ratings.stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(0);
            
            if (avgRating <= 2) {
                redFlags.add("Low average rating (" + String.format("%.1f", avgRating) + "/5)");
            }
            
            // Check for negative sentiment in reviews
            double avgSentiment = ratings.stream()
                    .filter(r -> r.getSentimentScore() != null)
                    .mapToDouble(Rating::getSentimentScore)
                    .average()
                    .orElse(0);
            
            if (avgSentiment < -0.3) {
                redFlags.add("Predominantly negative reviews");
            }
        }
        
        return redFlags;
    }
    
    @Override
    public Tenant updateTenantTrustScoreAndClassification(Tenant tenant, Integer trustScore, String classification) {
        if (tenant == null) {
            return null;
        }
        
        tenant.setTrustScore(trustScore);
        tenant.setClassification(classification);
        
        return tenantRepository.save(tenant);
    }
    
    // Helper method to create a "not found" response
    private TenantVerificationResponse createNotFoundResponse(String identifier) {
        TenantVerificationResponse response = new TenantVerificationResponse();
        response.setExists(false);
        response.setVerificationStatus("NOT_FOUND");
        response.setMessage("Tenant with identifier '" + identifier + "' not found");
        return response;
    }
    
    // Helper method to add rental history to the response
    private void addRentalHistoryToResponse(Tenant tenant, TenantVerificationResponse response) {
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByTenant(tenant);
        
        for (RentalHistory history : rentalHistories) {
            TenantVerificationResponse.RentalHistoryDTO historyDTO = new TenantVerificationResponse.RentalHistoryDTO();
            historyDTO.setPropertyAddress(history.getPropertyAddress());
            historyDTO.setLeaseStartDate(history.getLeaseStartDate());
            historyDTO.setLeaseEndDate(history.getLeaseEndDate());
            historyDTO.setRentAmount(history.getRentAmount());
            historyDTO.setOnTimePayments(history.getOnTimePayments());
            historyDTO.setLatePaymentsCount(history.getLatePaymentsCount());
            historyDTO.setPropertyDamage(history.getPropertyDamage());
            historyDTO.setDamageDescription(history.getDamageDescription());
            historyDTO.setHadDisputes(history.getHadDisputes());
            historyDTO.setDisputeDescription(history.getDisputeDescription());
            historyDTO.setEvictionFiled(history.getEvictionFiled());
            historyDTO.setEvictionReason(history.getEvictionReason());
            historyDTO.setLandlordName(history.getLandlord().getName());
            
            response.addRentalHistory(historyDTO);
        }
    }
    
    // Helper method to add ratings to the response
    private void addRatingsToResponse(Tenant tenant, TenantVerificationResponse response) {
        List<Rating> ratings = ratingRepository.findByTenantAndRatingType(tenant, "LANDLORD_TO_TENANT");
        
        for (Rating rating : ratings) {
            TenantVerificationResponse.RatingDTO ratingDTO = new TenantVerificationResponse.RatingDTO();
            ratingDTO.setLandlordName(rating.getLandlord().getName());
            ratingDTO.setRatingValue(rating.getRatingValue());
            ratingDTO.setReview(rating.getReview());
            ratingDTO.setPropertyAddress(rating.getPropertyAddress());
            ratingDTO.setRatingDate(rating.getCreatedAt() != null ? rating.getCreatedAt().toLocalDate() : null);
            
            // Add specific metrics if available
            if (rating.getPaymentTimeliness() != null) {
                ratingDTO.setPaymentTimeliness(rating.getPaymentTimeliness());
            }
            if (rating.getPropertyCare() != null) {
                ratingDTO.setPropertyCare(rating.getPropertyCare());
            }
            if (rating.getCommunication() != null) {
                ratingDTO.setCommunication(rating.getCommunication());
            }
            if (rating.getRuleAdherence() != null) {
                ratingDTO.setRuleAdherence(rating.getRuleAdherence());
            }
            if (rating.getCleanliness() != null) {
                ratingDTO.setCleanliness(rating.getCleanliness());
            }
            
            // Add detected traits if available
            if (rating.getDetectedTraits() != null && !rating.getDetectedTraits().isEmpty()) {
                List<String> traits = Arrays.stream(rating.getDetectedTraits().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                ratingDTO.setDetectedTraits(traits);
            }
            
            response.addRating(ratingDTO);
        }
    }
}