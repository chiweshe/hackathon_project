package com.hackathon.verification.rental.repository;

import com.hackathon.verification.rental.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    
    /**
     * Find a tenant by ID number
     * @param idNumber The ID number to search for
     * @return The tenant if found, null otherwise
     */
    Tenant findByIdNumber(String idNumber);
    
    /**
     * Find a tenant by phone number
     * @param phone The phone number to search for
     * @return The tenant if found, null otherwise
     */
    Tenant findByPhone(String phone);
    
    /**
     * Find tenants by name (case-insensitive, partial match)
     * @param name The name to search for
     * @return List of tenants matching the name
     */
    List<Tenant> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find tenants by current address (case-insensitive, partial match)
     * @param address The address to search for
     * @return List of tenants matching the address
     */
    List<Tenant> findByCurrentAddressContainingIgnoreCase(String address);
    
    /**
     * Find tenants by verification status
     * @param status The verification status to search for
     * @return List of tenants with the specified verification status
     */
    List<Tenant> findByVerificationStatus(String status);
    
    /**
     * Find tenants by classification
     * @param classification The classification to search for (Safe, Caution, Avoid)
     * @return List of tenants with the specified classification
     */
    List<Tenant> findByClassification(String classification);
    
    /**
     * Find tenants with trust score less than or equal to the specified value
     * @param maxScore The maximum trust score
     * @return List of tenants with trust score <= maxScore
     */
    List<Tenant> findByTrustScoreLessThanEqual(Integer maxScore);
    
    /**
     * Find tenants with trust score greater than or equal to the specified value
     * @param minScore The minimum trust score
     * @return List of tenants with trust score >= minScore
     */
    List<Tenant> findByTrustScoreGreaterThanEqual(Integer minScore);
}