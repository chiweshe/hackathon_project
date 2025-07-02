package com.hackathon.verification.vehicle.controller;

import com.hackathon.verification.vehicle.dto.CreateVehicleRequest;
import com.hackathon.verification.vehicle.dto.VehicleDTO;
import com.hackathon.verification.vehicle.dto.VehicleVerificationRequest;
import com.hackathon.verification.vehicle.dto.VehicleVerificationResponse;
import com.hackathon.verification.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicle Controller", description = "Operations for vehicle verification and management")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Verify vehicle", description = "Verifies if a vehicle exists and its status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle verification completed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleVerificationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input - chassis number or registration number must be provided")
    })
    @PostMapping("/verify")
    public ResponseEntity<VehicleVerificationResponse> verifyVehicle(
            @Parameter(description = "Vehicle verification request", required = true) @RequestBody VehicleVerificationRequest request) {
        VehicleVerificationResponse response = vehicleService.verifyVehicle(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Verify vehicle with AI", description = "Verifies vehicle with AI-enhanced analysis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "AI-enhanced vehicle verification completed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleVerificationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input - chassis number or registration number must be provided")
    })
    @PostMapping("/verify/ai")
    public ResponseEntity<VehicleVerificationResponse> verifyVehicleWithAI(
            @Parameter(description = "Vehicle verification request", required = true) @RequestBody VehicleVerificationRequest request) {
        VehicleVerificationResponse response = vehicleService.verifyVehicleWithAI(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Report vehicle as stolen", description = "Reports a vehicle as stolen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle reported as stolen successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PostMapping("/report/stolen/{chassisNumber}")
    public ResponseEntity<VehicleDTO> reportVehicleAsStolen(
            @Parameter(description = "Chassis number of the vehicle to report", required = true) @PathVariable String chassisNumber,
            @Parameter(description = "Details of the stolen report", required = true) @RequestParam String reportDetails) {
        VehicleDTO vehicle = vehicleService.reportVehicleAsStolen(chassisNumber, reportDetails);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Report vehicle as tampered", description = "Reports a vehicle as tampered with")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle reported as tampered successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PostMapping("/report/tampered/{chassisNumber}")
    public ResponseEntity<VehicleDTO> reportVehicleAsTampered(
            @Parameter(description = "Chassis number of the vehicle to report", required = true) @PathVariable String chassisNumber,
            @Parameter(description = "Details of the tampering report", required = true) @RequestParam String reportDetails) {
        VehicleDTO vehicle = vehicleService.reportVehicleAsTampered(chassisNumber, reportDetails);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Update vehicle ownership", description = "Updates the ownership information of a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle ownership updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PostMapping("/ownership/{chassisNumber}")
    public ResponseEntity<VehicleDTO> updateVehicleOwnership(
            @Parameter(description = "Chassis number of the vehicle", required = true) @PathVariable String chassisNumber,
            @Parameter(description = "Name of the new owner", required = true) @RequestParam String newOwnerName,
            @Parameter(description = "ID of the new owner", required = true) @RequestParam String newOwnerId) {
        VehicleDTO vehicle = vehicleService.updateVehicleOwnership(chassisNumber, newOwnerName, newOwnerId);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Create a new vehicle record", description = "Creates a new vehicle record with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehicle record created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or vehicle with same chassis/registration number already exists")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Vehicle information for creation",
            required = true,
            content = @Content(schema = @Schema(implementation = CreateVehicleRequest.class))
    )
    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(
            @RequestBody CreateVehicleRequest createVehicleRequest) {
        VehicleDTO createdVehicle = vehicleService.createVehicle(createVehicleRequest);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a vehicle record by ID", description = "Returns a vehicle record based on the provided ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle record found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle record not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(
            @Parameter(description = "ID of the vehicle record to retrieve", required = true) @PathVariable Long id) {
        VehicleDTO vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Get a vehicle record by chassis number", description = "Returns a vehicle record based on the provided chassis number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle record found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle record not found")
    })
    @GetMapping("/chassis/{chassisNumber}")
    public ResponseEntity<VehicleDTO> getVehicleByChassisNumber(
            @Parameter(description = "Chassis number of the vehicle record to retrieve", required = true) @PathVariable String chassisNumber) {
        VehicleDTO vehicle = vehicleService.getVehicleByChassisNumber(chassisNumber);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Get all vehicle records", description = "Returns a list of all vehicle records in the system")
    @ApiResponse(responseCode = "200", description = "List of vehicle records retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class)))
    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Update a vehicle record", description = "Updates an existing vehicle record with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle record updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or vehicle with same chassis/registration number already exists"),
        @ApiResponse(responseCode = "404", description = "Vehicle record not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(
            @Parameter(description = "ID of the vehicle record to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated vehicle information", required = true) @RequestBody VehicleDTO vehicleDTO) {
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.ok(updatedVehicle);
    }

    @Operation(summary = "Delete a vehicle record", description = "Deletes a vehicle record with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehicle record deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle record not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @Parameter(description = "ID of the vehicle record to delete", required = true) @PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get stolen vehicles", description = "Returns a list of all vehicles reported as stolen")
    @ApiResponse(responseCode = "200", description = "List of stolen vehicles retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class)))
    @GetMapping("/stolen")
    public ResponseEntity<List<VehicleDTO>> getStolenVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getStolenVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get tampered vehicles", description = "Returns a list of all vehicles reported as tampered with")
    @ApiResponse(responseCode = "200", description = "List of tampered vehicles retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class)))
    @GetMapping("/tampered")
    public ResponseEntity<List<VehicleDTO>> getTamperedVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getTamperedVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Search vehicles by make", description = "Returns vehicles that match the specified make")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class)))
    @GetMapping("/search/make")
    public ResponseEntity<List<VehicleDTO>> searchVehiclesByMake(
            @Parameter(description = "Make to search for", required = true) @RequestParam String make) {
        List<VehicleDTO> vehicles = vehicleService.searchVehiclesByMake(make);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Search vehicles by model", description = "Returns vehicles that match the specified model")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleDTO.class)))
    @GetMapping("/search/model")
    public ResponseEntity<List<VehicleDTO>> searchVehiclesByModel(
            @Parameter(description = "Model to search for", required = true) @RequestParam String model) {
        List<VehicleDTO> vehicles = vehicleService.searchVehiclesByModel(model);
        return ResponseEntity.ok(vehicles);
    }
}
