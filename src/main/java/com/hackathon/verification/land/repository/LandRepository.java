package com.hackathon.verification.land.repository;

import com.hackathon.verification.land.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LandRepository extends JpaRepository<Land, Long> {
    
    // Find land by stand number
    Optional<Land> findByStandNumber(String standNumber);
    
    // Find land by stand number and location
    Optional<Land> findByStandNumberAndLocation(String standNumber, String location);
    
    // Find lands by location
    List<Land> findByLocationContainingIgnoreCase(String location);
    
    // Find lands by owner name
    List<Land> findByOwnerNameContainingIgnoreCase(String ownerName);
    
    // Find lands by owner ID number
    List<Land> findByOwnerIdNumber(String ownerIdNumber);
    
    // Find lands by allocation status
    List<Land> findByIsAllocated(boolean isAllocated);
    
    // Find lands by property type
    List<Land> findByPropertyType(String propertyType);
    
    // Find lands by verification status
    List<Land> findByVerificationStatus(String verificationStatus);
    
    // Check if a land with the given stand number exists
    boolean existsByStandNumber(String standNumber);
    
    // Check if a land with the given stand number and location exists
    boolean existsByStandNumberAndLocation(String standNumber, String location);
}