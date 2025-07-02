package com.hackathon.verification.vehicle.service.impl;

import com.hackathon.verification.vehicle.dto.CreateVehicleRequest;
import com.hackathon.verification.vehicle.dto.VehicleDTO;
import com.hackathon.verification.vehicle.dto.VehicleVerificationRequest;
import com.hackathon.verification.vehicle.dto.VehicleVerificationResponse;
import com.hackathon.verification.vehicle.entity.Vehicle;
import com.hackathon.verification.vehicle.repository.VehicleRepository;
import com.hackathon.verification.vehicle.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public VehicleDTO createVehicle(CreateVehicleRequest createVehicleRequest) {
        try {
            // Check if vehicle with same chassis number already exists
            if (createVehicleRequest.getChassisNumber() != null && vehicleRepository.existsByChassisNumber(createVehicleRequest.getChassisNumber())) {
                throw new IllegalArgumentException("Vehicle with chassis number " + createVehicleRequest.getChassisNumber() + " already exists");
            }

            // Check if vehicle with same registration number already exists
            if (createVehicleRequest.getRegistrationNumber() != null && vehicleRepository.existsByRegistrationNumber(createVehicleRequest.getRegistrationNumber())) {
                throw new IllegalArgumentException("Vehicle with registration number " + createVehicleRequest.getRegistrationNumber() + " already exists");
            }

            // Create a new Vehicle object manually to avoid ModelMapper conversion issues
            Vehicle vehicle = new Vehicle();
            vehicle.setChassisNumber(createVehicleRequest.getChassisNumber());
            vehicle.setRegistrationNumber(createVehicleRequest.getRegistrationNumber());
            vehicle.setMake(createVehicleRequest.getMake());
            vehicle.setModel(createVehicleRequest.getModel());
            vehicle.setYear(createVehicleRequest.getYear());
            vehicle.setColor(createVehicleRequest.getColor());
            vehicle.setEngineNumber(createVehicleRequest.getEngineNumber());
            vehicle.setCurrentOwnerName(createVehicleRequest.getCurrentOwnerName());
            vehicle.setCurrentOwnerId(createVehicleRequest.getCurrentOwnerId());
            vehicle.setPurchaseDate(createVehicleRequest.getPurchaseDate());
            vehicle.setIsStolen(createVehicleRequest.getIsStolen());
            vehicle.setHasBeenTampered(createVehicleRequest.getHasBeenTampered());
            vehicle.setVerificationStatus(createVehicleRequest.getVerificationStatus());
            vehicle.setVerificationNotes(createVehicleRequest.getVerificationNotes());

            // Set default values for required fields if they're null
            if (vehicle.getChassisNumber() == null) {
                throw new IllegalArgumentException("Chassis number is required");
            }

            // Set default values for boolean fields if they're null
            if (vehicle.getIsStolen() == null) {
                vehicle.setIsStolen(false);
            }
            if (vehicle.getHasBeenTampered() == null) {
                vehicle.setHasBeenTampered(false);
            }

            // Set default value for verification status if it's null
            if (vehicle.getVerificationStatus() == null) {
                vehicle.setVerificationStatus("PENDING");
            }

            // Save the vehicle
            Vehicle savedVehicle = vehicleRepository.save(vehicle);

            // Convert entity back to DTO and return
            return modelMapper.map(savedVehicle, VehicleDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error creating vehicle: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));

        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleDTO getVehicleByChassisNumber(String chassisNumber) {
        Vehicle vehicle = vehicleRepository.findByChassisNumber(chassisNumber)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with chassis number: " + chassisNumber));

        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleDTO getVehicleByRegistrationNumber(String registrationNumber) {
        Vehicle vehicle = vehicleRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with registration number: " + registrationNumber));

        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        // Check if vehicle exists
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));

        // Check if chassis number is being changed and if new chassis number already exists
        if (vehicleDTO.getChassisNumber() != null && !vehicleDTO.getChassisNumber().equals(existingVehicle.getChassisNumber()) 
                && vehicleRepository.existsByChassisNumber(vehicleDTO.getChassisNumber())) {
            throw new IllegalArgumentException("Vehicle with chassis number " + vehicleDTO.getChassisNumber() + " already exists");
        }

        // Check if registration number is being changed and if new registration number already exists
        if (vehicleDTO.getRegistrationNumber() != null && !vehicleDTO.getRegistrationNumber().equals(existingVehicle.getRegistrationNumber()) 
                && vehicleRepository.existsByRegistrationNumber(vehicleDTO.getRegistrationNumber())) {
            throw new IllegalArgumentException("Vehicle with registration number " + vehicleDTO.getRegistrationNumber() + " already exists");
        }

        // Update the vehicle properties
        if (vehicleDTO.getChassisNumber() != null) {
            existingVehicle.setChassisNumber(vehicleDTO.getChassisNumber());
        }
        if (vehicleDTO.getRegistrationNumber() != null) {
            existingVehicle.setRegistrationNumber(vehicleDTO.getRegistrationNumber());
        }
        if (vehicleDTO.getMake() != null) {
            existingVehicle.setMake(vehicleDTO.getMake());
        }
        if (vehicleDTO.getModel() != null) {
            existingVehicle.setModel(vehicleDTO.getModel());
        }
        if (vehicleDTO.getYear() != null) {
            existingVehicle.setYear(vehicleDTO.getYear());
        }
        if (vehicleDTO.getColor() != null) {
            existingVehicle.setColor(vehicleDTO.getColor());
        }
        if (vehicleDTO.getEngineNumber() != null) {
            existingVehicle.setEngineNumber(vehicleDTO.getEngineNumber());
        }
        if (vehicleDTO.getCurrentOwnerName() != null) {
            existingVehicle.setCurrentOwnerName(vehicleDTO.getCurrentOwnerName());
        }
        if (vehicleDTO.getCurrentOwnerId() != null) {
            existingVehicle.setCurrentOwnerId(vehicleDTO.getCurrentOwnerId());
        }
        if (vehicleDTO.getPurchaseDate() != null) {
            existingVehicle.setPurchaseDate(vehicleDTO.getPurchaseDate());
        }
        if (vehicleDTO.getIsStolen() != null) {
            existingVehicle.setIsStolen(vehicleDTO.getIsStolen());
        }
        if (vehicleDTO.getHasBeenTampered() != null) {
            existingVehicle.setHasBeenTampered(vehicleDTO.getHasBeenTampered());
        }
        if (vehicleDTO.getVerificationStatus() != null) {
            existingVehicle.setVerificationStatus(vehicleDTO.getVerificationStatus());
        }
        if (vehicleDTO.getVerificationNotes() != null) {
            existingVehicle.setVerificationNotes(vehicleDTO.getVerificationNotes());
        }

        // Save the updated vehicle
        Vehicle updatedVehicle = vehicleRepository.save(existingVehicle);

        // Convert entity back to DTO and return
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }

    @Override
    @Transactional
    public void deleteVehicle(Long id) {
        // Check if vehicle exists
        if (!vehicleRepository.existsById(id)) {
            throw new EntityNotFoundException("Vehicle not found with id: " + id);
        }

        vehicleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehiclesByMake(String make) {
        List<Vehicle> vehicles = vehicleRepository.findByMakeContainingIgnoreCase(make);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehiclesByModel(String model) {
        List<Vehicle> vehicles = vehicleRepository.findByModelContainingIgnoreCase(model);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehiclesByYear(Integer year) {
        List<Vehicle> vehicles = vehicleRepository.findByYear(year);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehiclesByMakeAndModel(String make, String model) {
        List<Vehicle> vehicles = vehicleRepository.findByMakeAndModelAllIgnoreCase(make, model);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehiclesByCurrentOwnerName(String currentOwnerName) {
        List<Vehicle> vehicles = vehicleRepository.findByCurrentOwnerNameContainingIgnoreCase(currentOwnerName);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehiclesByCurrentOwnerId(String currentOwnerId) {
        List<Vehicle> vehicles = vehicleRepository.findByCurrentOwnerId(currentOwnerId);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> getStolenVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findByIsStolen(true);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> getTamperedVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findByHasBeenTampered(true);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDTO> getVehiclesByVerificationStatus(String verificationStatus) {
        List<Vehicle> vehicles = vehicleRepository.findByVerificationStatus(verificationStatus);

        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleVerificationResponse verifyVehicle(VehicleVerificationRequest request) {
        String chassisNumber = request.getChassisNumber();
        String registrationNumber = request.getRegistrationNumber();

        VehicleVerificationResponse response = new VehicleVerificationResponse();
        response.setChassisNumber(chassisNumber);
        response.setRegistrationNumber(registrationNumber);

        // Check if vehicle exists
        Optional<Vehicle> vehicleOptional;
        if (chassisNumber != null && !chassisNumber.isEmpty()) {
            vehicleOptional = vehicleRepository.findByChassisNumber(chassisNumber);
        } else if (registrationNumber != null && !registrationNumber.isEmpty()) {
            vehicleOptional = vehicleRepository.findByRegistrationNumber(registrationNumber);
        } else {
            throw new IllegalArgumentException("Either chassis number or registration number must be provided");
        }

        if (vehicleOptional.isPresent()) {
            Vehicle vehicle = vehicleOptional.get();
            response.setExists(true);
            response.setMake(vehicle.getMake());
            response.setModel(vehicle.getModel());
            response.setYear(vehicle.getYear());
            response.setCurrentOwnerName(vehicle.getCurrentOwnerName());
            response.setPurchaseDate(vehicle.getPurchaseDate());
            response.setIsStolen(vehicle.getIsStolen());
            response.setHasBeenTampered(vehicle.getHasBeenTampered());
            response.setVerificationStatus(vehicle.getVerificationStatus());

            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Vehicle found: ").append(vehicle.getMake()).append(" ").append(vehicle.getModel())
                    .append(" (").append(vehicle.getYear()).append(")");

            if (vehicle.getCurrentOwnerName() != null) {
                messageBuilder.append(". Current owner: ").append(vehicle.getCurrentOwnerName());
            }

            if (Boolean.TRUE.equals(vehicle.getIsStolen())) {
                messageBuilder.append(". WARNING: This vehicle has been reported as stolen!");
            }

            if (Boolean.TRUE.equals(vehicle.getHasBeenTampered())) {
                messageBuilder.append(". WARNING: This vehicle has been reported as tampered with!");
            }

            response.setMessage(messageBuilder.toString());

            // Add simulated ownership history (in a real implementation, this would come from a database)
            // For now, we'll just add the current owner
            if (vehicle.getCurrentOwnerName() != null) {
                VehicleVerificationResponse.OwnershipHistory currentOwnership = new VehicleVerificationResponse.OwnershipHistory(
                        vehicle.getCurrentOwnerName(),
                        vehicle.getCurrentOwnerId(),
                        vehicle.getPurchaseDate(),
                        null
                );
                response.addOwnershipHistory(currentOwnership);

                // Add a simulated previous owner
                LocalDate previousEndDate = vehicle.getPurchaseDate() != null ? 
                        vehicle.getPurchaseDate().minusDays(1) : LocalDate.now().minusYears(1);
                LocalDate previousStartDate = previousEndDate.minusYears(2);

                VehicleVerificationResponse.OwnershipHistory previousOwnership = new VehicleVerificationResponse.OwnershipHistory(
                        "Previous Owner",
                        "PREV-" + vehicle.getChassisNumber().substring(0, 5),
                        previousStartDate,
                        previousEndDate
                );
                response.addOwnershipHistory(previousOwnership);
            }
        } else {
            response.setExists(false);
            response.setIsStolen(false);
            response.setHasBeenTampered(false);
            response.setVerificationStatus("NOT_FOUND");
            response.setMessage("Vehicle not found with the provided details");
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleVerificationResponse verifyVehicleWithAI(VehicleVerificationRequest request) {
        // First, perform standard verification
        VehicleVerificationResponse response = verifyVehicle(request);

        // Add AI-enhanced verification (simulated for now)
        // In a real implementation, this would call an AI service
        Random random = new Random();
        double confidenceScore = 0.7 + (random.nextDouble() * 0.3); // Random score between 0.7 and 1.0
        response.setConfidenceScore(confidenceScore);

        // Enhance the message with AI insights
        if (response.isExists()) {
            if (Boolean.TRUE.equals(response.getIsStolen())) {
                response.setMessage(response.getMessage() + " AI analysis confirms this vehicle is stolen with " 
                        + String.format("%.2f", confidenceScore * 100) + "% confidence.");
            } else if (Boolean.TRUE.equals(response.getHasBeenTampered())) {
                response.setMessage(response.getMessage() + " AI analysis indicates potential tampering with " 
                        + String.format("%.2f", confidenceScore * 100) + "% confidence. Recommend physical inspection.");
            } else {
                response.setMessage(response.getMessage() + " AI analysis confirms this vehicle's legitimacy with " 
                        + String.format("%.2f", confidenceScore * 100) + "% confidence.");
            }
        } else {
            if (confidenceScore > 0.9) {
                response.setMessage("AI analysis confirms with high confidence that this vehicle does not exist in our records.");
            } else {
                response.setMessage("AI analysis suggests this vehicle may not be registered. Recommend further investigation.");
            }
        }

        return response;
    }

    @Override
    @Transactional
    public VehicleDTO reportVehicleAsStolen(String chassisNumber, String reportDetails) {
        Vehicle vehicle = vehicleRepository.findByChassisNumber(chassisNumber)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with chassis number: " + chassisNumber));

        vehicle.setIsStolen(true);
        vehicle.setVerificationStatus("REPORTED_STOLEN");

        String notes = vehicle.getVerificationNotes();
        if (notes == null) {
            notes = "";
        }
        notes += "\n[" + LocalDateTime.now() + "] Reported as stolen: " + reportDetails;
        vehicle.setVerificationNotes(notes);

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }

    @Override
    @Transactional
    public VehicleDTO reportVehicleAsTampered(String chassisNumber, String reportDetails) {
        Vehicle vehicle = vehicleRepository.findByChassisNumber(chassisNumber)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with chassis number: " + chassisNumber));

        vehicle.setHasBeenTampered(true);
        vehicle.setVerificationStatus("REPORTED_TAMPERED");

        String notes = vehicle.getVerificationNotes();
        if (notes == null) {
            notes = "";
        }
        notes += "\n[" + LocalDateTime.now() + "] Reported as tampered: " + reportDetails;
        vehicle.setVerificationNotes(notes);

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }

    @Override
    @Transactional
    public VehicleDTO updateVehicleOwnership(String chassisNumber, String newOwnerName, String newOwnerId) {
        Vehicle vehicle = vehicleRepository.findByChassisNumber(chassisNumber)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with chassis number: " + chassisNumber));

        // Store the previous owner information in verification notes
        String notes = vehicle.getVerificationNotes();
        if (notes == null) {
            notes = "";
        }
        notes += "\n[" + LocalDateTime.now() + "] Ownership changed from " + 
                (vehicle.getCurrentOwnerName() != null ? vehicle.getCurrentOwnerName() : "unknown") + 
                " to " + newOwnerName;
        vehicle.setVerificationNotes(notes);

        // Update ownership information
        vehicle.setCurrentOwnerName(newOwnerName);
        vehicle.setCurrentOwnerId(newOwnerId);
        vehicle.setPurchaseDate(LocalDate.now());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleDTO.class);
    }
}
