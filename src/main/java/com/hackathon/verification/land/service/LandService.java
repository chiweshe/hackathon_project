package com.hackathon.verification.land.service;

import com.hackathon.verification.land.dto.CreateLandRequest;
import com.hackathon.verification.land.dto.LandDTO;
import com.hackathon.verification.land.dto.LandVerificationRequest;
import com.hackathon.verification.land.dto.LandVerificationResponse;

import java.util.List;

public interface LandService {

    // Create a new land record
    LandDTO createLand(CreateLandRequest createLandRequest);

    // Get a land record by ID
    LandDTO getLandById(Long id);

    // Get a land record by stand number
    LandDTO getLandByStandNumber(String standNumber);

    // Get a land record by stand number and location
    LandDTO getLandByStandNumberAndLocation(String standNumber, String location);

    // Get all land records
    List<LandDTO> getAllLands();

    // Update a land record
    LandDTO updateLand(Long id, LandDTO landDTO);

    // Delete a land record
    void deleteLand(Long id);

    // Search lands by location
    List<LandDTO> searchLandsByLocation(String location);

    // Search lands by owner name
    List<LandDTO> searchLandsByOwnerName(String ownerName);

    // Search lands by owner ID number
    List<LandDTO> searchLandsByOwnerIdNumber(String ownerIdNumber);

    // Get lands by allocation status
    List<LandDTO> getLandsByAllocationStatus(boolean isAllocated);

    // Get lands by property type
    List<LandDTO> getLandsByPropertyType(String propertyType);

    // Get lands by verification status
    List<LandDTO> getLandsByVerificationStatus(String verificationStatus);

    // Verify a land record
    LandVerificationResponse verifyLand(LandVerificationRequest request);

    // AI-enhanced land verification
    LandVerificationResponse verifyLandWithAI(LandVerificationRequest request);
}
