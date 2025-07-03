package com.hackathon.verification.land.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.Map;

/**
 * Response DTO for land document verification.
 * Contains the verification result, extracted fields, and confidence scores.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LandDocumentVerificationResponse {
    
    private boolean matched;
    private String message;
    private String verificationStatus;
    
    // Extracted fields from the document
    private String extractedStandNumber;
    private String extractedOwnerName;
    private String extractedIdNumber;
    
    // Database record fields (if found)
    private String recordStandNumber;
    private String recordOwnerName;
    private String recordIdNumber;
    
    // Confidence scores for OCR extraction (0-100)
    private Map<String, Integer> confidenceScores;
    
    // Default constructor
    public LandDocumentVerificationResponse() {
        this.confidenceScores = new HashMap<>();
    }
    
    // Constructor with essential fields
    public LandDocumentVerificationResponse(boolean matched, String message, String verificationStatus) {
        this.matched = matched;
        this.message = message;
        this.verificationStatus = verificationStatus;
        this.confidenceScores = new HashMap<>();
    }
    
    // Getters and Setters
    public boolean isMatched() {
        return matched;
    }
    
    public void setMatched(boolean matched) {
        this.matched = matched;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getVerificationStatus() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
    
    public String getExtractedStandNumber() {
        return extractedStandNumber;
    }
    
    public void setExtractedStandNumber(String extractedStandNumber) {
        this.extractedStandNumber = extractedStandNumber;
    }
    
    public String getExtractedOwnerName() {
        return extractedOwnerName;
    }
    
    public void setExtractedOwnerName(String extractedOwnerName) {
        this.extractedOwnerName = extractedOwnerName;
    }
    
    public String getExtractedIdNumber() {
        return extractedIdNumber;
    }
    
    public void setExtractedIdNumber(String extractedIdNumber) {
        this.extractedIdNumber = extractedIdNumber;
    }
    
    public String getRecordStandNumber() {
        return recordStandNumber;
    }
    
    public void setRecordStandNumber(String recordStandNumber) {
        this.recordStandNumber = recordStandNumber;
    }
    
    public String getRecordOwnerName() {
        return recordOwnerName;
    }
    
    public void setRecordOwnerName(String recordOwnerName) {
        this.recordOwnerName = recordOwnerName;
    }
    
    public String getRecordIdNumber() {
        return recordIdNumber;
    }
    
    public void setRecordIdNumber(String recordIdNumber) {
        this.recordIdNumber = recordIdNumber;
    }
    
    public Map<String, Integer> getConfidenceScores() {
        return confidenceScores;
    }
    
    public void setConfidenceScores(Map<String, Integer> confidenceScores) {
        this.confidenceScores = confidenceScores;
    }
    
    // Helper method to add a confidence score
    public void addConfidenceScore(String field, Integer score) {
        this.confidenceScores.put(field, score);
    }
}