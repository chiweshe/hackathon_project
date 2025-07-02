package com.hackathon.verification.rental.repository;

import com.hackathon.verification.rental.entity.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    
    // Find landlord by ID number
    Optional<Landlord> findByIdNumber(String idNumber);
    
    // Find landlords by name
    List<Landlord> findByNameContainingIgnoreCase(String name);
    
    // Find landlords by email
    Optional<Landlord> findByEmail(String email);
    
    // Find landlords by phone
    Optional<Landlord> findByPhone(String phone);
    
    // Find landlords by address
    List<Landlord> findByAddressContainingIgnoreCase(String address);
    
    // Find landlords by verification status
    List<Landlord> findByVerificationStatus(String verificationStatus);
    
    // Find landlords with rating above a certain value
    List<Landlord> findByAverageRatingGreaterThanEqual(Double rating);
    
    // Find landlords with rating below a certain value
    List<Landlord> findByAverageRatingLessThanEqual(Double rating);
    
    // Find landlords with a minimum number of ratings
    List<Landlord> findByTotalRatingsGreaterThanEqual(Integer totalRatings);
    
    // Check if a landlord with the given ID number exists
    boolean existsByIdNumber(String idNumber);
    
    // Check if a landlord with the given email exists
    boolean existsByEmail(String email);
}