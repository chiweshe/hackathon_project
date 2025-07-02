package com.hackathon.verification.rental.service.impl;

import com.hackathon.verification.rental.dto.RatingDTO;
import com.hackathon.verification.rental.entity.Landlord;
import com.hackathon.verification.rental.entity.Rating;
import com.hackathon.verification.rental.entity.Tenant;
import com.hackathon.verification.rental.repository.LandlordRepository;
import com.hackathon.verification.rental.repository.RatingRepository;
import com.hackathon.verification.rental.repository.TenantRepository;
import com.hackathon.verification.rental.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
    
    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private LandlordRepository landlordRepository;
    
    @Autowired
    private TenantRepository tenantRepository;
    
    @Override
    public Rating createRating(RatingDTO ratingDTO) {
        logger.info("Creating new rating from {} to {}", 
                ratingDTO.getRatingType().equals("LANDLORD_TO_TENANT") ? "landlord" : "tenant",
                ratingDTO.getRatingType().equals("LANDLORD_TO_TENANT") ? "tenant" : "landlord");
        
        // Find landlord and tenant
        Landlord landlord = landlordRepository.findById(ratingDTO.getLandlordId())
                .orElseThrow(() -> new IllegalArgumentException("Landlord not found with ID: " + ratingDTO.getLandlordId()));
        
        Tenant tenant = tenantRepository.findById(ratingDTO.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + ratingDTO.getTenantId()));
        
        // Create new rating entity
        Rating rating = new Rating();
        rating.setLandlord(landlord);
        rating.setTenant(tenant);
        rating.setRatingValue(ratingDTO.getRatingValue());
        rating.setReview(ratingDTO.getReview());
        rating.setRatingType(ratingDTO.getRatingType());
        rating.setPropertyAddress(ratingDTO.getPropertyAddress());
        
        // Set lease dates if provided
        if (ratingDTO.getLeaseStartDate() != null) {
            rating.setLeaseStartDate(ratingDTO.getLeaseStartDate().atStartOfDay());
        }
        
        if (ratingDTO.getLeaseEndDate() != null) {
            rating.setLeaseEndDate(ratingDTO.getLeaseEndDate().atStartOfDay());
        }
        
        // Set specific rating metrics based on rating type
        if ("LANDLORD_TO_TENANT".equals(ratingDTO.getRatingType())) {
            rating.setPaymentTimeliness(ratingDTO.getPaymentTimeliness());
            rating.setPropertyCare(ratingDTO.getPropertyCare());
            rating.setCommunication(ratingDTO.getCommunication());
            rating.setRuleAdherence(ratingDTO.getRuleAdherence());
            rating.setCleanliness(ratingDTO.getCleanliness());
            
            // Update tenant's average rating
            tenant.addRating(ratingDTO.getRatingValue());
            tenantRepository.save(tenant);
        } else if ("TENANT_TO_LANDLORD".equals(ratingDTO.getRatingType())) {
            rating.setResponsiveness(ratingDTO.getResponsiveness());
            rating.setMaintenanceQuality(ratingDTO.getMaintenanceQuality());
            rating.setFairness(ratingDTO.getFairness());
            rating.setDepositHandling(ratingDTO.getDepositHandling());
            rating.setPrivacyRespect(ratingDTO.getPrivacyRespect());
            
            // Update landlord's average rating
            landlord.addRating(ratingDTO.getRatingValue());
            landlordRepository.save(landlord);
        }
        
        // Analyze sentiment and extract traits if review is provided
        if (ratingDTO.getReview() != null && !ratingDTO.getReview().isEmpty()) {
            Double sentimentScore = analyzeSentiment(ratingDTO.getReview());
            String traits = extractTraits(ratingDTO.getReview());
            
            rating.setSentimentScore(sentimentScore);
            rating.setDetectedTraits(traits);
        }
        
        // Save and return the rating
        return ratingRepository.save(rating);
    }
    
    @Override
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Rating> getRatingsByLandlord(Long landlordId) {
        Landlord landlord = landlordRepository.findById(landlordId)
                .orElseThrow(() -> new IllegalArgumentException("Landlord not found with ID: " + landlordId));
        
        return ratingRepository.findByLandlord(landlord);
    }
    
    @Override
    public List<Rating> getRatingsByTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
        
        return ratingRepository.findByTenant(tenant);
    }
    
    @Override
    public List<Rating> getRatingsByProperty(String propertyAddress) {
        return ratingRepository.findByPropertyAddressContainingIgnoreCase(propertyAddress);
    }
    
    @Override
    public List<Rating> getLandlordToTenantRatings(Long landlordId) {
        Landlord landlord = landlordRepository.findById(landlordId)
                .orElseThrow(() -> new IllegalArgumentException("Landlord not found with ID: " + landlordId));
        
        return ratingRepository.findByLandlordAndRatingType(landlord, "LANDLORD_TO_TENANT");
    }
    
    @Override
    public List<Rating> getTenantToLandlordRatings(Long landlordId) {
        Landlord landlord = landlordRepository.findById(landlordId)
                .orElseThrow(() -> new IllegalArgumentException("Landlord not found with ID: " + landlordId));
        
        return ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");
    }
    
    @Override
    public Rating updateRating(Long id, RatingDTO ratingDTO) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with ID: " + id));
        
        // Update basic fields
        existingRating.setRatingValue(ratingDTO.getRatingValue());
        existingRating.setReview(ratingDTO.getReview());
        existingRating.setPropertyAddress(ratingDTO.getPropertyAddress());
        
        // Update lease dates if provided
        if (ratingDTO.getLeaseStartDate() != null) {
            existingRating.setLeaseStartDate(ratingDTO.getLeaseStartDate().atStartOfDay());
        }
        
        if (ratingDTO.getLeaseEndDate() != null) {
            existingRating.setLeaseEndDate(ratingDTO.getLeaseEndDate().atStartOfDay());
        }
        
        // Update specific rating metrics based on rating type
        if ("LANDLORD_TO_TENANT".equals(existingRating.getRatingType())) {
            existingRating.setPaymentTimeliness(ratingDTO.getPaymentTimeliness());
            existingRating.setPropertyCare(ratingDTO.getPropertyCare());
            existingRating.setCommunication(ratingDTO.getCommunication());
            existingRating.setRuleAdherence(ratingDTO.getRuleAdherence());
            existingRating.setCleanliness(ratingDTO.getCleanliness());
        } else if ("TENANT_TO_LANDLORD".equals(existingRating.getRatingType())) {
            existingRating.setResponsiveness(ratingDTO.getResponsiveness());
            existingRating.setMaintenanceQuality(ratingDTO.getMaintenanceQuality());
            existingRating.setFairness(ratingDTO.getFairness());
            existingRating.setDepositHandling(ratingDTO.getDepositHandling());
            existingRating.setPrivacyRespect(ratingDTO.getPrivacyRespect());
        }
        
        // Re-analyze sentiment and extract traits if review is updated
        if (ratingDTO.getReview() != null && !ratingDTO.getReview().isEmpty()) {
            Double sentimentScore = analyzeSentiment(ratingDTO.getReview());
            String traits = extractTraits(ratingDTO.getReview());
            
            existingRating.setSentimentScore(sentimentScore);
            existingRating.setDetectedTraits(traits);
        }
        
        // Save and return the updated rating
        return ratingRepository.save(existingRating);
    }
    
    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
    
    @Override
    public Double analyzeSentiment(String reviewText) {
        // In a real implementation, this would use a natural language processing API or library
        // For this example, we'll use a simple simulation
        
        // List of positive and negative words
        List<String> positiveWords = Arrays.asList(
                "good", "great", "excellent", "amazing", "wonderful", "fantastic", "outstanding",
                "helpful", "responsive", "clean", "reliable", "honest", "fair", "professional",
                "recommend", "satisfied", "happy", "pleased", "impressed", "positive", "best"
        );
        
        List<String> negativeWords = Arrays.asList(
                "bad", "poor", "terrible", "awful", "horrible", "disappointing", "unresponsive",
                "dirty", "messy", "unreliable", "dishonest", "unfair", "unprofessional", "rude",
                "avoid", "dissatisfied", "unhappy", "unimpressed", "negative", "worst", "late",
                "damage", "broken", "issue", "problem", "complaint", "delay", "neglect"
        );
        
        // Convert to lowercase and split into words
        String[] words = reviewText.toLowerCase().split("\\W+");
        
        // Count positive and negative words
        int positiveCount = 0;
        int negativeCount = 0;
        
        for (String word : words) {
            if (positiveWords.contains(word)) {
                positiveCount++;
            } else if (negativeWords.contains(word)) {
                negativeCount++;
            }
        }
        
        // Calculate sentiment score (-1 to 1)
        if (positiveCount == 0 && negativeCount == 0) {
            return 0.0; // Neutral
        }
        
        return (double) (positiveCount - negativeCount) / (positiveCount + negativeCount);
    }
    
    @Override
    public String extractTraits(String reviewText) {
        // In a real implementation, this would use a natural language processing API or library
        // For this example, we'll use a simple simulation
        
        // Map of keywords to traits
        List<String> cleanlinessKeywords = Arrays.asList("clean", "tidy", "neat", "organized", "spotless", "dirty", "messy", "unclean");
        List<String> responsivenessKeywords = Arrays.asList("responsive", "quick", "prompt", "timely", "slow", "unresponsive", "delayed");
        List<String> reliabilityKeywords = Arrays.asList("reliable", "dependable", "consistent", "unreliable", "inconsistent");
        List<String> respectfulKeywords = Arrays.asList("respectful", "considerate", "polite", "disrespectful", "rude", "inconsiderate");
        List<String> communicationKeywords = Arrays.asList("communicative", "clear", "informative", "uncommunicative", "unclear");
        
        // Convert to lowercase and split into words
        String[] words = reviewText.toLowerCase().split("\\W+");
        List<String> wordList = Arrays.asList(words);
        
        // Detect traits
        List<String> detectedTraits = Arrays.asList(
                checkTrait(wordList, cleanlinessKeywords, "Clean", "Messy"),
                checkTrait(wordList, responsivenessKeywords, "Responsive", "Unresponsive"),
                checkTrait(wordList, reliabilityKeywords, "Reliable", "Unreliable"),
                checkTrait(wordList, respectfulKeywords, "Respectful", "Disrespectful"),
                checkTrait(wordList, communicationKeywords, "Communicative", "Uncommunicative")
        ).stream()
                .filter(trait -> trait != null)
                .collect(Collectors.toList());
        
        // Return comma-separated list of traits
        return String.join(", ", detectedTraits);
    }
    
    // Helper method to check for traits based on keywords
    private String checkTrait(List<String> words, List<String> keywords, String positiveTrait, String negativeTrait) {
        int positiveCount = 0;
        int negativeCount = 0;
        
        for (String word : words) {
            int index = keywords.indexOf(word);
            if (index >= 0) {
                if (index < keywords.size() / 2) {
                    positiveCount++;
                } else {
                    negativeCount++;
                }
            }
        }
        
        if (positiveCount > negativeCount && positiveCount > 0) {
            return positiveTrait;
        } else if (negativeCount > positiveCount && negativeCount > 0) {
            return negativeTrait;
        }
        
        return null;
    }
}