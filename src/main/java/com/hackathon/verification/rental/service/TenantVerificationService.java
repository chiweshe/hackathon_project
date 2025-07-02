package com.hackathon.verification.rental.service;

import com.hackathon.verification.rental.dto.TenantVerificationRequest;
import com.hackathon.verification.rental.dto.TenantVerificationResponse;
import com.hackathon.verification.rental.entity.Tenant;

import java.util.List;

public interface TenantVerificationService {
    
    /**
     * Verify a tenant based on the provided verification request
     * @param request The verification request containing identifier information
     * @return A verification response with tenant details and verification results
     */
    TenantVerificationResponse verifyTenant(TenantVerificationRequest request);
    
    /**
     * Find a tenant by identifier (name, ID number, phone, or address)
     * @param identifier The identifier to search for
     * @param identifierType The type of identifier (NAME, ID_NUMBER, PHONE, ADDRESS)
     * @return The tenant if found, null otherwise
     */
    Tenant findTenantByIdentifier(String identifier, String identifierType);
    
    /**
     * Calculate the trust score for a tenant based on rental history and ratings
     * @param tenant The tenant to calculate the trust score for
     * @return A trust score between 0 and 100
     */
    Integer calculateTrustScore(Tenant tenant);
    
    /**
     * Determine the classification of a tenant based on trust score and other factors
     * @param tenant The tenant to classify
     * @param trustScore The calculated trust score
     * @return A classification string ("Safe", "Caution", or "Avoid")
     */
    String determineClassification(Tenant tenant, Integer trustScore);
    
    /**
     * Generate a behavioral summary for a tenant using AI analysis of reviews and history
     * @param tenant The tenant to generate a summary for
     * @return A behavioral summary string
     */
    String generateBehavioralSummary(Tenant tenant);
    
    /**
     * Identify red flags in a tenant's rental history and ratings
     * @param tenant The tenant to identify red flags for
     * @return A list of red flag strings
     */
    List<String> identifyRedFlags(Tenant tenant);
    
    /**
     * Update a tenant's trust score and classification in the database
     * @param tenant The tenant to update
     * @param trustScore The new trust score
     * @param classification The new classification
     * @return The updated tenant
     */
    Tenant updateTenantTrustScoreAndClassification(Tenant tenant, Integer trustScore, String classification);
}