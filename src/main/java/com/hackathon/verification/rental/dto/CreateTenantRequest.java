package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTenantRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "ID number is required")
    private String idNumber;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Invalid phone number format")
    private String phone;
    
    private String currentAddress;
    
    private String employmentStatus;
    
    private String employer;
    
    private Double monthlyIncome;
    
    // Default constructor
    public CreateTenantRequest() {
    }
    
    // Constructor with fields
    public CreateTenantRequest(String name, String idNumber, String email, String phone, 
                              String currentAddress, String employmentStatus, 
                              String employer, Double monthlyIncome) {
        this.name = name;
        this.idNumber = idNumber;
        this.email = email;
        this.phone = phone;
        this.currentAddress = currentAddress;
        this.employmentStatus = employmentStatus;
        this.employer = employer;
        this.monthlyIncome = monthlyIncome;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIdNumber() {
        return idNumber;
    }
    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCurrentAddress() {
        return currentAddress;
    }
    
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
    
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public String getEmployer() {
        return employer;
    }
    
    public void setEmployer(String employer) {
        this.employer = employer;
    }
    
    public Double getMonthlyIncome() {
        return monthlyIncome;
    }
    
    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
}