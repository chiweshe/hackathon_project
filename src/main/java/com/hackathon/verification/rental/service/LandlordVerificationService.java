package com.hackathon.verification.rental.service;

import com.hackathon.verification.rental.dto.LandlordVerificationRequest;
import com.hackathon.verification.rental.dto.LandlordVerificationResponse;
import com.hackathon.verification.rental.entity.Landlord;

import java.util.List;

public interface LandlordVerificationService {
    
    /**
     * Verify a landlord based on the provided verification request
     * @param request The verification request containing identifier information
     * @return A verification response with landlord details and verification results
     */
    LandlordVerificationResponse verifyLandlord(LandlordVerificationRequest request);
    
    /**
     * Find a landlord by identifier (name, ID number, phone, address, or property address)
     * @param identifier The identifier to search for
     * @param identifierType The type of identifier (NAME, ID_NUMBER, PHONE, ADDRESS, PROPERTY_ADDRESS)
     * @return The landlord if found, null otherwise
     */
    Landlord findLandlordByIdentifier(String identifier, String identifierType);
    
    /**
     * Calculate the trust score for a landlord based on rental history and ratings
     * @param landlord The landlord to calculate the trust score for
     * @return A trust score between 0 and 100
     */
    Integer calculateTrustScore(Landlord landlord);
    
    /**
     * Calculate the responsiveness score for a landlord based on ratings
     * @param landlord The landlord to calculate the score for
     * @return A responsiveness score between 0 and 100
     */
    Integer calculateResponsivenessScore(Landlord landlord);
    
    /**
     * Calculate the fairness score for a landlord based on ratings
     * @param landlord The landlord to calculate the score for
     * @return A fairness score between 0 and 100
     */
    Integer calculateFairnessScore(Landlord landlord);
    
    /**
     * Calculate the deposit return rate for a landlord based on rental history
     * @param landlord The landlord to calculate the rate for
     * @return A percentage of security deposits returned in full
     */
    Double calculateDepositReturnRate(Landlord landlord);
    
    /**
     * Determine the classification of a landlord based on trust score and other factors
     * @param landlord The landlord to classify
     * @param trustScore The calculated trust score
     * @return A classification string ("Safe", "Caution", or "Avoid")
     */
    String determineClassification(Landlord landlord, Integer trustScore);
    
    /**
     * Generate a behavioral summary for a landlord using AI analysis of reviews and history
     * @param landlord The landlord to generate a summary for
     * @return A behavioral summary string
     */
    String generateBehavioralSummary(Landlord landlord);
    
    /**
     * Identify red flags in a landlord's rental history and ratings
     * @param landlord The landlord to identify red flags for
     * @return A list of red flag strings
     */
    List<String> identifyRedFlags(Landlord landlord);
    
    /**
     * Update a landlord's trust score and classification in the database
     * @param landlord The landlord to update
     * @param trustScore The new trust score
     * @param classification The new classification
     * @return The updated landlord
     */
    Landlord updateLandlordTrustScoreAndClassification(Landlord landlord, Integer trustScore, String classification);
}