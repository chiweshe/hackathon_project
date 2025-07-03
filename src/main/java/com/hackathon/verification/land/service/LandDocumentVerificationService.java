package com.hackathon.verification.land.service;

import com.hackathon.verification.land.dto.LandDocumentVerificationRequest;
import com.hackathon.verification.land.dto.LandDocumentVerificationResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for land document verification.
 * Provides methods for OCR processing and verification of land documents.
 */
public interface LandDocumentVerificationService {
    
    /**
     * Verify a land document by extracting information using OCR and comparing with database records.
     * 
     * @param request The verification request containing the document image
     * @return A verification response with the result and extracted information
     */
    LandDocumentVerificationResponse verifyLandDocument(LandDocumentVerificationRequest request);
    
    /**
     * Extract text from an image using OCR.
     * 
     * @param imageFile The image file to process
     * @return The extracted text
     */
    String extractTextFromImage(MultipartFile imageFile);
    
    /**
     * Parse the extracted text to find specific fields (stand number, owner name, ID number).
     * 
     * @param extractedText The text extracted from the document
     * @return A verification response with the extracted fields
     */
    LandDocumentVerificationResponse parseExtractedText(String extractedText);
    
    /**
     * Compare the extracted fields with database records to verify ownership.
     * 
     * @param extractedStandNumber The stand number extracted from the document
     * @param extractedOwnerName The owner name extracted from the document
     * @param extractedIdNumber The ID number extracted from the document
     * @return A verification response with the verification result
     */
    LandDocumentVerificationResponse verifyOwnership(String extractedStandNumber, String extractedOwnerName, String extractedIdNumber);
}