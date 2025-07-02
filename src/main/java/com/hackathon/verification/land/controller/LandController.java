package com.hackathon.verification.land.controller;

import com.hackathon.verification.land.dto.CreateLandRequest;
import com.hackathon.verification.land.dto.LandDTO;
import com.hackathon.verification.land.dto.LandVerificationRequest;
import com.hackathon.verification.land.dto.LandVerificationResponse;
import com.hackathon.verification.land.service.LandService;
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
@RequestMapping("/api/lands")
@Tag(name = "Land Controller", description = "Operations for land verification and management")
public class LandController {

    private final LandService landService;

    @Autowired
    public LandController(LandService landService) {
        this.landService = landService;
    }

    @Operation(summary = "Verify land", description = "Verifies if a land exists and its allocation status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Land verification completed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandVerificationResponse.class)))
    })
    @PostMapping("/verify")
    public ResponseEntity<LandVerificationResponse> verifyLand(
            @Parameter(description = "Land verification request", required = true) @RequestBody LandVerificationRequest request) {
        LandVerificationResponse response = landService.verifyLand(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Verify land with AI", description = "Verifies land with AI-enhanced analysis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "AI-enhanced land verification completed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandVerificationResponse.class)))
    })
    @PostMapping("/verify/ai")
    public ResponseEntity<LandVerificationResponse> verifyLandWithAI(
            @Parameter(description = "Land verification request", required = true) @RequestBody LandVerificationRequest request) {
        LandVerificationResponse response = landService.verifyLandWithAI(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new land record", description = "Creates a new land record with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Land record created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or land with same stand number already exists")
    })
    @PostMapping
    public ResponseEntity<LandDTO> createLand(
            @Parameter(description = "Land information for creation", required = true) @RequestBody CreateLandRequest createLandRequest) {
        LandDTO createdLand = landService.createLand(createLandRequest);
        return new ResponseEntity<>(createdLand, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a land record by ID", description = "Returns a land record based on the provided ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Land record found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class))),
        @ApiResponse(responseCode = "404", description = "Land record not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LandDTO> getLandById(
            @Parameter(description = "ID of the land record to retrieve", required = true) @PathVariable Long id) {
        LandDTO land = landService.getLandById(id);
        return ResponseEntity.ok(land);
    }

    @Operation(summary = "Get a land record by stand number", description = "Returns a land record based on the provided stand number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Land record found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class))),
        @ApiResponse(responseCode = "404", description = "Land record not found")
    })
    @GetMapping("/stand/{standNumber}")
    public ResponseEntity<LandDTO> getLandByStandNumber(
            @Parameter(description = "Stand number of the land record to retrieve", required = true) @PathVariable String standNumber) {
        LandDTO land = landService.getLandByStandNumber(standNumber);
        return ResponseEntity.ok(land);
    }

    @Operation(summary = "Get all land records", description = "Returns a list of all land records in the system")
    @ApiResponse(responseCode = "200", description = "List of land records retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class)))
    @GetMapping
    public ResponseEntity<List<LandDTO>> getAllLands() {
        List<LandDTO> lands = landService.getAllLands();
        return ResponseEntity.ok(lands);
    }

    @Operation(summary = "Update a land record", description = "Updates an existing land record with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Land record updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or land with same stand number already exists"),
        @ApiResponse(responseCode = "404", description = "Land record not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LandDTO> updateLand(
            @Parameter(description = "ID of the land record to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated land information", required = true) @RequestBody LandDTO landDTO) {
        LandDTO updatedLand = landService.updateLand(id, landDTO);
        return ResponseEntity.ok(updatedLand);
    }

    @Operation(summary = "Delete a land record", description = "Deletes a land record with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Land record deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Land record not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLand(
            @Parameter(description = "ID of the land record to delete", required = true) @PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search lands by location", description = "Returns land records that match the specified location")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class)))
    @GetMapping("/search/location")
    public ResponseEntity<List<LandDTO>> searchLandsByLocation(
            @Parameter(description = "Location to search for", required = true) @RequestParam String location) {
        List<LandDTO> lands = landService.searchLandsByLocation(location);
        return ResponseEntity.ok(lands);
    }

    @Operation(summary = "Search lands by owner name", description = "Returns land records that match the specified owner name")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class)))
    @GetMapping("/search/owner")
    public ResponseEntity<List<LandDTO>> searchLandsByOwnerName(
            @Parameter(description = "Owner name to search for", required = true) @RequestParam String ownerName) {
        List<LandDTO> lands = landService.searchLandsByOwnerName(ownerName);
        return ResponseEntity.ok(lands);
    }

    @Operation(summary = "Get lands by allocation status", description = "Returns land records with the specified allocation status")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDTO.class)))
    @GetMapping("/search/allocated/{isAllocated}")
    public ResponseEntity<List<LandDTO>> getLandsByAllocationStatus(
            @Parameter(description = "Allocation status to search for", required = true) @PathVariable boolean isAllocated) {
        List<LandDTO> lands = landService.getLandsByAllocationStatus(isAllocated);
        return ResponseEntity.ok(lands);
    }
}
