package com.hackathon.verification.rental.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateLandlordRequest {
    
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
    
    private String address;
    
    private List<String> managedProperties;
    
    // Default constructor
    public CreateLandlordRequest() {
    }
    
    // Constructor with fields
    public CreateLandlordRequest(String name, String idNumber, String email, String phone, 
                               String address, List<String> managedProperties) {
        this.name = name;
        this.idNumber = idNumber;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.managedProperties = managedProperties;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<String> getManagedProperties() {
        return managedProperties;
    }
    
    public void setManagedProperties(List<String> managedProperties) {
        this.managedProperties = managedProperties;
    }
}