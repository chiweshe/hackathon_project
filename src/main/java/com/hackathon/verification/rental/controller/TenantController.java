package com.hackathon.verification.rental.controller;

import com.hackathon.verification.rental.dto.CreateTenantRequest;
import com.hackathon.verification.rental.dto.TenantVerificationRequest;
import com.hackathon.verification.rental.dto.TenantVerificationResponse;
import com.hackathon.verification.rental.entity.Tenant;
import com.hackathon.verification.rental.repository.TenantRepository;
import com.hackathon.verification.rental.service.TenantVerificationService;
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

@RestController
@CrossOrigin
@RequestMapping("/api/v1/tenants")
@Tag(name = "Tenant API", description = "API for tenant management and verification")
public class TenantController {

    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantVerificationService tenantVerificationService;

    @PostMapping
    @Operation(summary = "Create a new tenant", description = "Creates a new tenant with the provided information")
    public ResponseEntity<Tenant> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        logger.info("Creating new tenant: {}", request.getName());

        // Check if tenant with same ID number already exists
        Tenant existingTenant = tenantRepository.findByIdNumber(request.getIdNumber());
        if (existingTenant != null) {
            logger.warn("Tenant with ID number {} already exists", request.getIdNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Create new tenant entity
        Tenant tenant = new Tenant(
                request.getName(),
                request.getIdNumber(),
                request.getEmail(),
                request.getPhone(),
                request.getCurrentAddress(),
                request.getEmploymentStatus(),
                request.getEmployer(),
                request.getMonthlyIncome()
        );

        // Save and return the tenant
        Tenant savedTenant = tenantRepository.save(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTenant);
    }

    @GetMapping
    @Operation(summary = "Get all tenants", description = "Retrieves a list of all tenants")
    public ResponseEntity<List<Tenant>> getAllTenants() {
        logger.info("Retrieving all tenants");

        List<Tenant> tenants = tenantRepository.findAll();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tenant by ID", description = "Retrieves a tenant by their ID")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long id) {
        logger.info("Retrieving tenant with ID: {}", id);

        return tenantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search tenants", description = "Searches for tenants by name, ID number, or phone")
    public ResponseEntity<ApiResponse<List<Tenant>>> searchTenants(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String idNumber,
            @RequestParam(required = false) String phone) {

        logger.info("Searching for tenants with name: {}, idNumber: {}, phone: {}", name, idNumber, phone);

        if (idNumber != null && !idNumber.isEmpty()) {
            Tenant tenant = tenantRepository.findByIdNumber(idNumber);
            if (tenant == null) {
                return ResponseEntity.ok(ApiResponse.emptyList("No tenant found with ID number: " + idNumber));
            }
            return ResponseEntity.ok(new ApiResponse<>(List.of(tenant)));
        } else if (phone != null && !phone.isEmpty()) {
            Tenant tenant = tenantRepository.findByPhone(phone);
            if (tenant == null) {
                return ResponseEntity.ok(ApiResponse.emptyList("No tenant found with phone: " + phone));
            }
            return ResponseEntity.ok(new ApiResponse<>(List.of(tenant)));
        } else if (name != null && !name.isEmpty()) {
            List<Tenant> tenants = tenantRepository.findByNameContainingIgnoreCase(name);
            if (tenants.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.emptyList("No tenants found with name containing: " + name));
            }
            return ResponseEntity.ok(new ApiResponse<>(tenants));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update tenant", description = "Updates an existing tenant with the provided information")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @Valid @RequestBody CreateTenantRequest request) {
        logger.info("Updating tenant with ID: {}", id);

        return tenantRepository.findById(id)
                .map(tenant -> {
                    tenant.setName(request.getName());
                    tenant.setEmail(request.getEmail());
                    tenant.setPhone(request.getPhone());
                    tenant.setCurrentAddress(request.getCurrentAddress());
                    tenant.setEmploymentStatus(request.getEmploymentStatus());
                    tenant.setEmployer(request.getEmployer());
                    tenant.setMonthlyIncome(request.getMonthlyIncome());

                    Tenant updatedTenant = tenantRepository.save(tenant);
                    return ResponseEntity.ok(updatedTenant);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tenant", description = "Deletes a tenant by their ID")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        logger.info("Deleting tenant with ID: {}", id);

        if (!tenantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        tenantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    @Operation(summary = "Verify tenant", description = "Verifies a tenant based on the provided information")
    public ResponseEntity<TenantVerificationResponse> verifyTenant(@Valid @RequestBody TenantVerificationRequest request) {
        logger.info("Verifying tenant with identifier: {}, type: {}", 
                   request.getIdentifier(), request.getIdentifierType());

        TenantVerificationResponse response = tenantVerificationService.verifyTenant(request);

        if (!response.isExists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
