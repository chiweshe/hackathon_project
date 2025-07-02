package com.hackathon.verification.rental.repository;

import com.hackathon.verification.rental.entity.Rating;
import com.hackathon.verification.rental.entity.Landlord;
import com.hackathon.verification.rental.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    // Find ratings by landlord
    List<Rating> findByLandlord(Landlord landlord);
    
    // Find ratings by tenant
    List<Rating> findByTenant(Tenant tenant);
    
    // Find ratings by landlord ID
    List<Rating> findByLandlordId(Long landlordId);
    
    // Find ratings by tenant ID
    List<Rating> findByTenantId(Long tenantId);
    
    // Find ratings by rating type
    List<Rating> findByRatingType(String ratingType);
    
    // Find ratings by property address
    List<Rating> findByPropertyAddressContainingIgnoreCase(String propertyAddress);
    
    // Find ratings by rating value greater than or equal to a value
    List<Rating> findByRatingValueGreaterThanEqual(Double ratingValue);
    
    // Find ratings by rating value less than or equal to a value
    List<Rating> findByRatingValueLessThanEqual(Double ratingValue);
    
    // Find ratings by sentiment score greater than or equal to a value
    List<Rating> findBySentimentScoreGreaterThanEqual(Double sentimentScore);
    
    // Find ratings by sentiment score less than or equal to a value
    List<Rating> findBySentimentScoreLessThanEqual(Double sentimentScore);
    
    // Find ratings by lease start date after a certain date
    List<Rating> findByLeaseStartDateAfter(LocalDateTime date);
    
    // Find ratings by lease end date before a certain date
    List<Rating> findByLeaseEndDateBefore(LocalDateTime date);
    
    // Find ratings by landlord and tenant
    List<Rating> findByLandlordAndTenant(Landlord landlord, Tenant tenant);
    
    // Find ratings by landlord and rating type
    List<Rating> findByLandlordAndRatingType(Landlord landlord, String ratingType);
    
    // Find ratings by tenant and rating type
    List<Rating> findByTenantAndRatingType(Tenant tenant, String ratingType);
    
    // Find ratings created after a certain date
    List<Rating> findByCreatedAtAfter(LocalDateTime date);
}