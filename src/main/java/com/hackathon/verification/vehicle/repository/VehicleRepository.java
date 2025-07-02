package com.hackathon.verification.vehicle.repository;

import com.hackathon.verification.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    // Find vehicle by chassis number
    Optional<Vehicle> findByChassisNumber(String chassisNumber);
    
    // Find vehicle by registration number
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
    
    // Find vehicles by make
    List<Vehicle> findByMakeContainingIgnoreCase(String make);
    
    // Find vehicles by model
    List<Vehicle> findByModelContainingIgnoreCase(String model);
    
    // Find vehicles by year
    List<Vehicle> findByYear(Integer year);
    
    // Find vehicles by make and model
    List<Vehicle> findByMakeAndModelAllIgnoreCase(String make, String model);
    
    // Find vehicles by current owner name
    List<Vehicle> findByCurrentOwnerNameContainingIgnoreCase(String currentOwnerName);
    
    // Find vehicles by current owner ID
    List<Vehicle> findByCurrentOwnerId(String currentOwnerId);
    
    // Find stolen vehicles
    List<Vehicle> findByIsStolen(Boolean isStolen);
    
    // Find tampered vehicles
    List<Vehicle> findByHasBeenTampered(Boolean hasBeenTampered);
    
    // Find vehicles by verification status
    List<Vehicle> findByVerificationStatus(String verificationStatus);
    
    // Check if a vehicle with the given chassis number exists
    boolean existsByChassisNumber(String chassisNumber);
    
    // Check if a vehicle with the given registration number exists
    boolean existsByRegistrationNumber(String registrationNumber);
}