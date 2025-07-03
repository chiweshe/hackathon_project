package com.hackathon.verification.land.service.impl;

import com.hackathon.verification.land.dto.LandDocumentVerificationRequest;
import com.hackathon.verification.land.dto.LandDocumentVerificationResponse;
import com.hackathon.verification.land.entity.Land;
import com.hackathon.verification.land.repository.LandRepository;
import com.hackathon.verification.land.service.LandDocumentVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the LandDocumentVerificationService interface.
 * Provides OCR processing and verification of land documents.
 */
@Service
public class LandDocumentVerificationServiceImpl implements LandDocumentVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(LandDocumentVerificationServiceImpl.class);
    
    @Autowired
    private LandRepository landRepository;
    
    @Override
    public LandDocumentVerificationResponse verifyLandDocument(LandDocumentVerificationRequest request) {
        try {
            // Extract text from the document image
            String extractedText = extractTextFromImage(request.getDocumentImage());
            
            // Parse the extracted text to find specific fields
            LandDocumentVerificationResponse response = parseExtractedText(extractedText);
            
            // If the request contains known fields, use them to supplement the extracted fields
            if (request.getStandNumber() != null && response.getExtractedStandNumber() == null) {
                response.setExtractedStandNumber(request.getStandNumber());
            }
            
            if (request.getOwnerName() != null && response.getExtractedOwnerName() == null) {
                response.setExtractedOwnerName(request.getOwnerName());
            }
            
            if (request.getOwnerIdNumber() != null && response.getExtractedIdNumber() == null) {
                response.setExtractedIdNumber(request.getOwnerIdNumber());
            }
            
            // Verify ownership by comparing extracted fields with database records
            return verifyOwnership(
                response.getExtractedStandNumber(),
                response.getExtractedOwnerName(),
                response.getExtractedIdNumber()
            );
        } catch (Exception e) {
            logger.error("Error verifying land document", e);
            return new LandDocumentVerificationResponse(
                false,
                "Error processing document: " + e.getMessage(),
                "ERROR"
            );
        }
    }
    
    @Override
    public String extractTextFromImage(MultipartFile imageFile) {
        // TODO: Implement OCR using Tess4J once dependency issues are resolved
        // For now, return a placeholder message
        logger.info("OCR processing requested for file: {}", imageFile.getOriginalFilename());
        
        try {
            // Just log the file size for now
            logger.info("File size: {} bytes", imageFile.getSize());
            
            // Return a placeholder text that simulates extracted content from a land document
            return "STAND NUMBER: S12345\n" +
                   "OWNER: John Doe\n" +
                   "ID NUMBER: ID98765432\n" +
                   "LOCATION: Sample Location\n" +
                   "ALLOCATION DATE: 2023-01-15";
        } catch (Exception e) {
            logger.error("Error extracting text from image", e);
            return "";
        }
    }
    
    @Override
    public LandDocumentVerificationResponse parseExtractedText(String extractedText) {
        LandDocumentVerificationResponse response = new LandDocumentVerificationResponse();
        
        // Extract stand number using regex
        Pattern standPattern = Pattern.compile("STAND\\s*(?:NUMBER|NO|#)?\\s*[:\\-]?\\s*(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher standMatcher = standPattern.matcher(extractedText);
        if (standMatcher.find()) {
            response.setExtractedStandNumber(standMatcher.group(1));
            response.addConfidenceScore("standNumber", 85); // Placeholder confidence score
        }
        
        // Extract owner name using regex
        Pattern ownerPattern = Pattern.compile("OWNER\\s*[:\\-]?\\s*([\\w\\s]+)", Pattern.CASE_INSENSITIVE);
        Matcher ownerMatcher = ownerPattern.matcher(extractedText);
        if (ownerMatcher.find()) {
            response.setExtractedOwnerName(ownerMatcher.group(1).trim());
            response.addConfidenceScore("ownerName", 80); // Placeholder confidence score
        }
        
        // Extract ID number using regex
        Pattern idPattern = Pattern.compile("ID\\s*(?:NUMBER)?\\s*[:\\-]?\\s*(\\w+)", Pattern.CASE_INSENSITIVE);
        Matcher idMatcher = idPattern.matcher(extractedText);
        if (idMatcher.find()) {
            response.setExtractedIdNumber(idMatcher.group(1));
            response.addConfidenceScore("idNumber", 90); // Placeholder confidence score
        }
        
        return response;
    }
    
    @Override
    public LandDocumentVerificationResponse verifyOwnership(String extractedStandNumber, String extractedOwnerName, String extractedIdNumber) {
        LandDocumentVerificationResponse response = new LandDocumentVerificationResponse();
        response.setExtractedStandNumber(extractedStandNumber);
        response.setExtractedOwnerName(extractedOwnerName);
        response.setExtractedIdNumber(extractedIdNumber);
        
        // Check if we have enough information to verify
        if (extractedStandNumber == null && extractedOwnerName == null && extractedIdNumber == null) {
            response.setMatched(false);
            response.setVerificationStatus("INSUFFICIENT_DATA");
            response.setMessage("Could not extract sufficient data from the document for verification");
            return response;
        }
        
        // Try to find a matching land record
        Optional<Land> landRecord = Optional.empty();
        
        // First try to find by stand number (most reliable)
        if (extractedStandNumber != null) {
            landRecord = landRepository.findByStandNumber(extractedStandNumber);
        }
        
        // If not found and we have owner ID, try by owner ID
        if (landRecord.isEmpty() && extractedIdNumber != null) {
            List<Land> landsByOwnerId = landRepository.findByOwnerIdNumber(extractedIdNumber);
            if (!landsByOwnerId.isEmpty()) {
                landRecord = Optional.of(landsByOwnerId.get(0));
            }
        }
        
        // If still not found and we have owner name, try by owner name
        if (landRecord.isEmpty() && extractedOwnerName != null) {
            List<Land> landsByOwnerName = landRepository.findByOwnerNameContainingIgnoreCase(extractedOwnerName);
            if (!landsByOwnerName.isEmpty()) {
                landRecord = Optional.of(landsByOwnerName.get(0));
            }
        }
        
        // If a record was found, compare the extracted fields with the record
        if (landRecord.isPresent()) {
            Land land = landRecord.get();
            
            // Set the record fields in the response
            response.setRecordStandNumber(land.getStandNumber());
            response.setRecordOwnerName(land.getOwnerName());
            response.setRecordIdNumber(land.getOwnerIdNumber());
            
            // Check if the extracted fields match the record
            boolean standNumberMatches = extractedStandNumber != null && 
                                        extractedStandNumber.equalsIgnoreCase(land.getStandNumber());
            
            boolean ownerNameMatches = extractedOwnerName != null && 
                                      land.getOwnerName() != null &&
                                      land.getOwnerName().toLowerCase().contains(extractedOwnerName.toLowerCase());
            
            boolean idNumberMatches = extractedIdNumber != null && 
                                     land.getOwnerIdNumber() != null &&
                                     extractedIdNumber.equalsIgnoreCase(land.getOwnerIdNumber());
            
            // Determine the overall match result
            if (standNumberMatches && (ownerNameMatches || idNumberMatches)) {
                response.setMatched(true);
                response.setVerificationStatus("VERIFIED");
                response.setMessage("Document verification successful. Land ownership confirmed.");
            } else {
                response.setMatched(false);
                response.setVerificationStatus("MISMATCH");
                response.setMessage("Document verification failed. Extracted information does not match records.");
            }
        } else {
            // No matching record found
            response.setMatched(false);
            response.setVerificationStatus("NOT_FOUND");
            response.setMessage("No matching land record found in the database.");
        }
        
        return response;
    }
}