package com.hackathon.verification.rental.repository;

import com.hackathon.verification.rental.entity.RentalHistory;
import com.hackathon.verification.rental.entity.Tenant;
import com.hackathon.verification.rental.entity.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalHistoryRepository extends JpaRepository<RentalHistory, Long> {
    
    /**
     * Find all rental histories for a specific tenant
     * @param tenant The tenant to find histories for
     * @return List of rental histories
     */
    List<RentalHistory> findByTenant(Tenant tenant);
    
    /**
     * Find all rental histories for a specific landlord
     * @param landlord The landlord to find histories for
     * @return List of rental histories
     */
    List<RentalHistory> findByLandlord(Landlord landlord);
    
    /**
     * Find all rental histories for a specific property address
     * @param propertyAddress The property address to find histories for
     * @return List of rental histories
     */
    List<RentalHistory> findByPropertyAddressContainingIgnoreCase(String propertyAddress);
    
    /**
     * Find all rental histories for a specific tenant and landlord
     * @param tenant The tenant to find histories for
     * @param landlord The landlord to find histories for
     * @return List of rental histories
     */
    List<RentalHistory> findByTenantAndLandlord(Tenant tenant, Landlord landlord);
    
    /**
     * Find all rental histories where eviction was filed
     * @return List of rental histories with evictions
     */
    List<RentalHistory> findByEvictionFiledTrue();
    
    /**
     * Find all rental histories where property damage occurred
     * @return List of rental histories with property damage
     */
    List<RentalHistory> findByPropertyDamageTrue();
    
    /**
     * Find all rental histories where security deposit was not returned
     * @return List of rental histories without returned deposits
     */
    List<RentalHistory> findBySecurityDepositReturnedFalse();
    
    /**
     * Find all rental histories where disputes occurred
     * @return List of rental histories with disputes
     */
    List<RentalHistory> findByHadDisputesTrue();
}