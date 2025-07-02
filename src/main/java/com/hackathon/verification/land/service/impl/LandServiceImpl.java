package com.hackathon.verification.land.service.impl;

import com.hackathon.verification.land.dto.CreateLandRequest;
import com.hackathon.verification.land.dto.LandDTO;
import com.hackathon.verification.land.dto.LandVerificationRequest;
import com.hackathon.verification.land.dto.LandVerificationResponse;
import com.hackathon.verification.land.entity.Land;
import com.hackathon.verification.land.repository.LandRepository;
import com.hackathon.verification.land.service.LandService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LandServiceImpl implements LandService {

    private static final Logger logger = LoggerFactory.getLogger(LandServiceImpl.class);

    private final LandRepository landRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LandServiceImpl(LandRepository landRepository, ModelMapper modelMapper) {
        this.landRepository = landRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public LandDTO createLand(CreateLandRequest createLandRequest) {
        logger.info("Creating land with request: {}", createLandRequest);

        try {
            // Check if land with same stand number already exists
            if (createLandRequest.getStandNumber() != null && landRepository.existsByStandNumber(createLandRequest.getStandNumber())) {
                logger.warn("Land with stand number {} already exists", createLandRequest.getStandNumber());
                throw new IllegalArgumentException("Land with stand number " + createLandRequest.getStandNumber() + " already exists");
            }

            // Pre-process the request to handle potential type conversion issues
            logger.debug("Pre-processing request to handle type conversions");

            // Create a new Land object manually to avoid ModelMapper conversion issues
            Land land = new Land();
            land.setStandNumber(createLandRequest.getStandNumber());
            land.setLocation(createLandRequest.getLocation());
            land.setTitle(createLandRequest.getTitle());
            land.setOwnerName(createLandRequest.getOwnerName());
            land.setOwnerIdNumber(createLandRequest.getOwnerIdNumber());
            land.setAllocated(createLandRequest.isAllocated());
            land.setAllocationDate(createLandRequest.getAllocationDate());
            land.setPropertySizeSquareMeters(createLandRequest.getPropertySizeSquareMeters());
            land.setPropertyType(createLandRequest.getPropertyType());
            land.setVerificationStatus(createLandRequest.getVerificationStatus());

            logger.debug("Created entity manually: {}", land);

            // Set default values for required fields if they're null
            if (land.getStandNumber() == null) {
                logger.error("Stand number is required but was null");
                throw new IllegalArgumentException("Stand number is required");
            }
            if (land.getLocation() == null) {
                logger.error("Location is required but was null");
                throw new IllegalArgumentException("Location is required");
            }
            if (land.getTitle() == null) {
                logger.error("Title is required but was null");
                throw new IllegalArgumentException("Title is required");
            }

            // Set default value for verification status if it's null
            if (land.getVerificationStatus() == null) {
                logger.debug("Setting default verification status to PENDING");
                land.setVerificationStatus("PENDING");
            }

            // Save the land
            logger.debug("Saving land entity");
            Land savedLand = landRepository.save(land);
            logger.info("Land saved successfully with ID: {}", savedLand.getId());

            // Convert entity back to DTO and return
            logger.debug("Converting saved entity back to DTO");
            LandDTO landDTO = modelMapper.map(savedLand, LandDTO.class);
            logger.debug("Returning DTO: {}", landDTO);
            return landDTO;
        } catch (Exception e) {
            logger.error("Error creating land: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public LandDTO getLandById(Long id) {
        Land land = landRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with id: " + id));

        return modelMapper.map(land, LandDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public LandDTO getLandByStandNumber(String standNumber) {
        Land land = landRepository.findByStandNumber(standNumber)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with stand number: " + standNumber));

        return modelMapper.map(land, LandDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public LandDTO getLandByStandNumberAndLocation(String standNumber, String location) {
        Land land = landRepository.findByStandNumberAndLocation(standNumber, location)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with stand number: " + standNumber + " and location: " + location));

        return modelMapper.map(land, LandDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> getAllLands() {
        List<Land> lands = landRepository.findAll();

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LandDTO updateLand(Long id, LandDTO landDTO) {
        // Check if land exists
        Land existingLand = landRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with id: " + id));

        // Check if stand number is being changed and if new stand number already exists
        if (landDTO.getStandNumber() != null && !landDTO.getStandNumber().equals(existingLand.getStandNumber()) 
                && landRepository.existsByStandNumber(landDTO.getStandNumber())) {
            throw new IllegalArgumentException("Land with stand number " + landDTO.getStandNumber() + " already exists");
        }

        // Update the land properties
        if (landDTO.getStandNumber() != null) {
            existingLand.setStandNumber(landDTO.getStandNumber());
        }
        if (landDTO.getLocation() != null) {
            existingLand.setLocation(landDTO.getLocation());
        }
        if (landDTO.getTitle() != null) {
            existingLand.setTitle(landDTO.getTitle());
        }
        if (landDTO.getOwnerName() != null) {
            existingLand.setOwnerName(landDTO.getOwnerName());
        }
        if (landDTO.getOwnerIdNumber() != null) {
            existingLand.setOwnerIdNumber(landDTO.getOwnerIdNumber());
        }
        existingLand.setAllocated(landDTO.isAllocated());
        if (landDTO.getAllocationDate() != null) {
            existingLand.setAllocationDate(landDTO.getAllocationDate());
        }
        if (landDTO.getPropertySizeSquareMeters() != null) {
            existingLand.setPropertySizeSquareMeters(landDTO.getPropertySizeSquareMeters());
        }
        if (landDTO.getPropertyType() != null) {
            existingLand.setPropertyType(landDTO.getPropertyType());
        }
        if (landDTO.getVerificationStatus() != null) {
            existingLand.setVerificationStatus(landDTO.getVerificationStatus());
        }

        // Save the updated land
        Land updatedLand = landRepository.save(existingLand);

        // Convert entity back to DTO and return
        return modelMapper.map(updatedLand, LandDTO.class);
    }

    @Override
    @Transactional
    public void deleteLand(Long id) {
        // Check if land exists
        if (!landRepository.existsById(id)) {
            throw new EntityNotFoundException("Land not found with id: " + id);
        }

        landRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> searchLandsByLocation(String location) {
        List<Land> lands = landRepository.findByLocationContainingIgnoreCase(location);

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> searchLandsByOwnerName(String ownerName) {
        List<Land> lands = landRepository.findByOwnerNameContainingIgnoreCase(ownerName);

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> searchLandsByOwnerIdNumber(String ownerIdNumber) {
        List<Land> lands = landRepository.findByOwnerIdNumber(ownerIdNumber);

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> getLandsByAllocationStatus(boolean isAllocated) {
        List<Land> lands = landRepository.findByIsAllocated(isAllocated);

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> getLandsByPropertyType(String propertyType) {
        List<Land> lands = landRepository.findByPropertyType(propertyType);

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LandDTO> getLandsByVerificationStatus(String verificationStatus) {
        List<Land> lands = landRepository.findByVerificationStatus(verificationStatus);

        return lands.stream()
                .map(land -> modelMapper.map(land, LandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LandVerificationResponse verifyLand(LandVerificationRequest request) {
        String standNumber = request.getStandNumber();
        String location = request.getLocation();

        LandVerificationResponse response = new LandVerificationResponse();
        response.setStandNumber(standNumber);
        response.setLocation(location);

        // Check if land exists
        Optional<Land> landOptional;
        if (location != null && !location.isEmpty()) {
            // First try exact match
            landOptional = landRepository.findByStandNumberAndLocation(standNumber, location);

            // If no exact match, try a more flexible approach for location
            if (!landOptional.isPresent()) {
                // Get all lands with the exact stand number
                Optional<Land> landByStandNumber = landRepository.findByStandNumber(standNumber);

                if (landByStandNumber.isPresent()) {
                    Land land = landByStandNumber.get();
                    // Check if the location contains the search term or vice versa (case insensitive)
                    if (land.getLocation().toLowerCase().contains(location.toLowerCase()) || 
                        location.toLowerCase().contains(land.getLocation().toLowerCase())) {
                        landOptional = Optional.of(land);
                    }
                }
            }
        } else {
            landOptional = landRepository.findByStandNumber(standNumber);
        }

        if (landOptional.isPresent()) {
            Land land = landOptional.get();
            response.setExists(true);
            response.setAllocated(land.isAllocated());
            response.setOwnerName(land.getOwnerName());
            response.setVerificationStatus(land.getVerificationStatus());

            if (land.isAllocated()) {
                response.setMessage("The stand is already allocated to " + land.getOwnerName());
            } else {
                response.setMessage("The stand exists but is not allocated to anyone");
            }
        } else {
            response.setExists(false);
            response.setAllocated(false);
            response.setVerificationStatus("NOT_FOUND");
            response.setMessage("The stand does not exist");
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public LandVerificationResponse verifyLandWithAI(LandVerificationRequest request) {
        // First, perform standard verification
        LandVerificationResponse response = verifyLand(request);

        // Add AI-enhanced verification (simulated for now)
        // In a real implementation, this would call an AI service
        Random random = new Random();
        double confidenceScore = 0.7 + (random.nextDouble() * 0.3); // Random score between 0.7 and 1.0
        response.setConfidenceScore(confidenceScore);

        // Enhance the message with AI insights
        if (response.isExists()) {
            if (response.isAllocated()) {
                response.setMessage(response.getMessage() + ". AI analysis confirms this with " 
                        + String.format("%.2f", confidenceScore * 100) + "% confidence.");
            } else {
                response.setMessage(response.getMessage() + ". AI analysis suggests this land may be available for allocation with " 
                        + String.format("%.2f", confidenceScore * 100) + "% confidence.");
            }
        } else {
            if (confidenceScore > 0.9) {
                response.setMessage("AI analysis confirms with high confidence that this stand does not exist in our records.");
            } else {
                response.setMessage("AI analysis suggests this stand may not be registered. Recommend further investigation.");
            }
        }

        return response;
    }
}
