package com.hackathon.verification.land.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request DTO for land document verification.
 * Contains the document image to be processed for OCR.
 */
public class LandDocumentVerificationRequest {
    
    @JsonIgnore
    private MultipartFile documentImage;
    
    // Optional fields that can be provided if known
    private String standNumber;
    private String ownerName;
    private String ownerIdNumber;
    
    // Default constructor
    public LandDocumentVerificationRequest() {
    }
    
    // Constructor with document image
    public LandDocumentVerificationRequest(MultipartFile documentImage) {
        this.documentImage = documentImage;
    }
    
    // Constructor with all fields
    public LandDocumentVerificationRequest(MultipartFile documentImage, String standNumber, 
                                          String ownerName, String ownerIdNumber) {
        this.documentImage = documentImage;
        this.standNumber = standNumber;
        this.ownerName = ownerName;
        this.ownerIdNumber = ownerIdNumber;
    }
    
    // Getters and Setters
    public MultipartFile getDocumentImage() {
        return documentImage;
    }
    
    public void setDocumentImage(MultipartFile documentImage) {
        this.documentImage = documentImage;
    }
    
    public String getStandNumber() {
        return standNumber;
    }
    
    public void setStandNumber(String standNumber) {
        this.standNumber = standNumber;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getOwnerIdNumber() {
        return ownerIdNumber;
    }
    
    public void setOwnerIdNumber(String ownerIdNumber) {
        this.ownerIdNumber = ownerIdNumber;
    }
}