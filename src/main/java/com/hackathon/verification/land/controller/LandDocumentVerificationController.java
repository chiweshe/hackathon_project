package com.hackathon.verification.land.controller;

import com.hackathon.verification.land.dto.LandDocumentVerificationRequest;
import com.hackathon.verification.land.dto.LandDocumentVerificationResponse;
import com.hackathon.verification.land.service.LandDocumentVerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for land document verification.
 * Provides endpoints for verifying land ownership documents using OCR.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/lands")
@Tag(name = "Land Document Verification", description = "API for verifying land ownership documents using OCR")
public class LandDocumentVerificationController {

    private static final Logger logger = LoggerFactory.getLogger(LandDocumentVerificationController.class);
    
    @Autowired
    private LandDocumentVerificationService landDocumentVerificationService;
    
    @Operation(summary = "Verify land document", description = "Verifies a land document by extracting information using OCR and comparing with database records")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document verification completed",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LandDocumentVerificationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or missing document")
    })
    @PostMapping(value = "/verify/land-document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LandDocumentVerificationResponse> verifyLandDocument(
            @Parameter(description = "Document image file", required = true) 
            @RequestParam("document") MultipartFile document,
            
            @Parameter(description = "Stand number (optional, if known)") 
            @RequestParam(value = "standNumber", required = false) String standNumber,
            
            @Parameter(description = "Owner name (optional, if known)") 
            @RequestParam(value = "ownerName", required = false) String ownerName,
            
            @Parameter(description = "Owner ID number (optional, if known)") 
            @RequestParam(value = "ownerIdNumber", required = false) String ownerIdNumber) {
        
        logger.info("Received land document verification request for file: {}", document.getOriginalFilename());
        
        // Create the verification request
        LandDocumentVerificationRequest request = new LandDocumentVerificationRequest(
            document, standNumber, ownerName, ownerIdNumber
        );
        
        // Process the verification request
        LandDocumentVerificationResponse response = landDocumentVerificationService.verifyLandDocument(request);
        
        return ResponseEntity.ok(response);
    }
}