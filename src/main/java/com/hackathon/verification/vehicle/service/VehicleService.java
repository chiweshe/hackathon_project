package com.hackathon.verification.vehicle.service;

import com.hackathon.verification.vehicle.dto.CreateVehicleRequest;
import com.hackathon.verification.vehicle.dto.VehicleDTO;
import com.hackathon.verification.vehicle.dto.VehicleVerificationRequest;
import com.hackathon.verification.vehicle.dto.VehicleVerificationResponse;

import java.util.List;

public interface VehicleService {

    // Create a new vehicle record
    VehicleDTO createVehicle(CreateVehicleRequest createVehicleRequest);

    // Get a vehicle record by ID
    VehicleDTO getVehicleById(Long id);

    // Get a vehicle record by chassis number
    VehicleDTO getVehicleByChassisNumber(String chassisNumber);

    // Get a vehicle record by registration number
    VehicleDTO getVehicleByRegistrationNumber(String registrationNumber);

    // Get all vehicle records
    List<VehicleDTO> getAllVehicles();

    // Update a vehicle record
    VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO);

    // Delete a vehicle record
    void deleteVehicle(Long id);

    // Search vehicles by make
    List<VehicleDTO> searchVehiclesByMake(String make);

    // Search vehicles by model
    List<VehicleDTO> searchVehiclesByModel(String model);

    // Search vehicles by year
    List<VehicleDTO> searchVehiclesByYear(Integer year);

    // Search vehicles by make and model
    List<VehicleDTO> searchVehiclesByMakeAndModel(String make, String model);

    // Search vehicles by current owner name
    List<VehicleDTO> searchVehiclesByCurrentOwnerName(String currentOwnerName);

    // Search vehicles by current owner ID
    List<VehicleDTO> searchVehiclesByCurrentOwnerId(String currentOwnerId);

    // Get stolen vehicles
    List<VehicleDTO> getStolenVehicles();

    // Get tampered vehicles
    List<VehicleDTO> getTamperedVehicles();

    // Get vehicles by verification status
    List<VehicleDTO> getVehiclesByVerificationStatus(String verificationStatus);

    // Verify a vehicle record
    VehicleVerificationResponse verifyVehicle(VehicleVerificationRequest request);

    // AI-enhanced vehicle verification
    VehicleVerificationResponse verifyVehicleWithAI(VehicleVerificationRequest request);

    // Report a vehicle as stolen
    VehicleDTO reportVehicleAsStolen(String chassisNumber, String reportDetails);

    // Report a vehicle as tampered
    VehicleDTO reportVehicleAsTampered(String chassisNumber, String reportDetails);

    // Update vehicle ownership
    VehicleDTO updateVehicleOwnership(String chassisNumber, String newOwnerName, String newOwnerId);
}
