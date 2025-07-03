package com.hackathon.verification.rental.controller;

import com.hackathon.verification.rental.dto.CreateLandlordRequest;
import com.hackathon.verification.rental.dto.LandlordVerificationRequest;
import com.hackathon.verification.rental.dto.LandlordVerificationResponse;
import com.hackathon.verification.rental.entity.Landlord;
import com.hackathon.verification.rental.repository.LandlordRepository;
import com.hackathon.verification.rental.service.LandlordVerificationService;
import com.hackathon.verification.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/landlords")
@Tag(name = "Landlord API", description = "API for landlord management and verification")
public class LandlordController {

    private static final Logger logger = LoggerFactory.getLogger(LandlordController.class);

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private LandlordVerificationService landlordVerificationService;

    @PostMapping
    @Operation(summary = "Create a new landlord", description = "Creates a new landlord with the provided information")
    public ResponseEntity<Landlord> createLandlord(@Valid @RequestBody CreateLandlordRequest request) {
        logger.info("Creating new landlord: {}", request.getName());

        // Check if landlord with same ID number already exists
        if (landlordRepository.existsByIdNumber(request.getIdNumber())) {
            logger.warn("Landlord with ID number {} already exists", request.getIdNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Check if landlord with same email already exists
        if (landlordRepository.existsByEmail(request.getEmail())) {
            logger.warn("Landlord with email {} already exists", request.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Create new landlord entity
        Landlord landlord = new Landlord(
                request.getName(),
                request.getIdNumber(),
                request.getEmail(),
                request.getPhone(),
                request.getAddress()
        );

        // Set managed properties if provided
        if (request.getManagedProperties() != null && !request.getManagedProperties().isEmpty()) {
            String managedPropertiesStr = String.join(",", request.getManagedProperties());
            landlord.setManagedProperties(managedPropertiesStr);
        }

        // Save and return the landlord
        Landlord savedLandlord = landlordRepository.save(landlord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLandlord);
    }

    @GetMapping
    @Operation(summary = "Get all landlords", description = "Retrieves a list of all landlords")
    public ResponseEntity<List<Landlord>> getAllLandlords() {
        logger.info("Retrieving all landlords");

        List<Landlord> landlords = landlordRepository.findAll();
        return ResponseEntity.ok(landlords);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get landlord by ID", description = "Retrieves a landlord by their ID")
    public ResponseEntity<Landlord> getLandlordById(@PathVariable Long id) {
        logger.info("Retrieving landlord with ID: {}", id);

        return landlordRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search landlords", description = "Searches for landlords by name, ID number, email, phone, or address")
    public ResponseEntity<List<Landlord>> searchLandlords(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String idNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address) {

        logger.info("Searching for landlords with name: {}, idNumber: {}, email: {}, phone: {}, address: {}",
                   name, idNumber, email, phone, address);

        if (idNumber != null && !idNumber.isEmpty()) {
            return landlordRepository.findByIdNumber(idNumber)
                    .map(landlord -> ResponseEntity.ok(List.of(landlord)))
                    .orElse(ResponseEntity.ok(List.of()));
        } else if (email != null && !email.isEmpty()) {
            return landlordRepository.findByEmail(email)
                    .map(landlord -> ResponseEntity.ok(List.of(landlord)))
                    .orElse(ResponseEntity.ok(List.of()));
        } else if (phone != null && !phone.isEmpty()) {
            return landlordRepository.findByPhone(phone)
                    .map(landlord -> ResponseEntity.ok(List.of(landlord)))
                    .orElse(ResponseEntity.ok(List.of()));
        } else if (name != null && !name.isEmpty()) {
            List<Landlord> landlords = landlordRepository.findByNameContainingIgnoreCase(name);
            return ResponseEntity.ok(landlords);
        } else if (address != null && !address.isEmpty()) {
            List<Landlord> landlords = landlordRepository.findByAddressContainingIgnoreCase(address);
            return ResponseEntity.ok(landlords);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update landlord", description = "Updates an existing landlord with the provided information")
    public ResponseEntity<Landlord> updateLandlord(@PathVariable Long id, @Valid @RequestBody CreateLandlordRequest request) {
        logger.info("Updating landlord with ID: {}", id);

        return landlordRepository.findById(id)
                .map(landlord -> {
                    landlord.setName(request.getName());
                    landlord.setEmail(request.getEmail());
                    landlord.setPhone(request.getPhone());
                    landlord.setAddress(request.getAddress());

                    // Update managed properties if provided
                    if (request.getManagedProperties() != null && !request.getManagedProperties().isEmpty()) {
                        String managedPropertiesStr = String.join(",", request.getManagedProperties());
                        landlord.setManagedProperties(managedPropertiesStr);
                    }

                    Landlord updatedLandlord = landlordRepository.save(landlord);
                    return ResponseEntity.ok(updatedLandlord);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete landlord", description = "Deletes a landlord by their ID")
    public ResponseEntity<Void> deleteLandlord(@PathVariable Long id) {
        logger.info("Deleting landlord with ID: {}", id);

        if (!landlordRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        landlordRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify landlord", description = "Verifies a landlord based on the provided information")
    public ResponseEntity<LandlordVerificationResponse> verifyLandlord(@Valid @RequestBody LandlordVerificationRequest request) {
        logger.info("Verifying landlord with identifier: {}, type: {}", 
                   request.getIdentifier(), request.getIdentifierType());

        LandlordVerificationResponse response = landlordVerificationService.verifyLandlord(request);

        if (!response.isExists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
