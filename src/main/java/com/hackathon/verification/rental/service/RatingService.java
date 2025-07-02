package com.hackathon.verification.rental.service;

import com.hackathon.verification.rental.dto.RatingDTO;
import com.hackathon.verification.rental.entity.Rating;
import java.util.List;

/**
 * Service interface for managing ratings between landlords and tenants
 */
public interface RatingService {
    
    /**
     * Create a new rating
     * @param ratingDTO The rating data
     * @return The created rating
     */
    Rating createRating(RatingDTO ratingDTO);
    
    /**
     * Get a rating by ID
     * @param id The rating ID
     * @return The rating if found, null otherwise
     */
    Rating getRatingById(Long id);
    
    /**
     * Get all ratings for a landlord
     * @param landlordId The landlord ID
     * @return List of ratings
     */
    List<Rating> getRatingsByLandlord(Long landlordId);
    
    /**
     * Get all ratings for a tenant
     * @param tenantId The tenant ID
     * @return List of ratings
     */
    List<Rating> getRatingsByTenant(Long tenantId);
    
    /**
     * Get all ratings for a property
     * @param propertyAddress The property address
     * @return List of ratings
     */
    List<Rating> getRatingsByProperty(String propertyAddress);
    
    /**
     * Get all ratings from a landlord to tenants
     * @param landlordId The landlord ID
     * @return List of ratings
     */
    List<Rating> getLandlordToTenantRatings(Long landlordId);
    
    /**
     * Get all ratings from tenants to a landlord
     * @param landlordId The landlord ID
     * @return List of ratings
     */
    List<Rating> getTenantToLandlordRatings(Long landlordId);
    
    /**
     * Update an existing rating
     * @param id The rating ID
     * @param ratingDTO The updated rating data
     * @return The updated rating
     */
    Rating updateRating(Long id, RatingDTO ratingDTO);
    
    /**
     * Delete a rating
     * @param id The rating ID
     */
    void deleteRating(Long id);
    
    /**
     * Analyze sentiment in a review text
     * @param reviewText The review text to analyze
     * @return Sentiment score between -1 (negative) and 1 (positive)
     */
    Double analyzeSentiment(String reviewText);
    
    /**
     * Extract traits from a review text
     * @param reviewText The review text to analyze
     * @return Comma-separated list of traits
     */
    String extractTraits(String reviewText);
}