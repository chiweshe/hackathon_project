package com.hackathon.verification.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 * Generic API response wrapper that includes data and a message.
 * @param <T> The type of data being returned
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private T data;
    private String message;
    private boolean success;
    
    // Default constructor
    public ApiResponse() {
        this.success = true;
    }
    
    // Constructor with data
    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
    }
    
    // Constructor with data and message
    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.success = true;
    }
    
    // Constructor with message and success flag
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    
    // Static factory method for empty list response
    public static <E> ApiResponse<List<E>> emptyList(String message) {
        return new ApiResponse<>(List.of(), message);
    }
    
    // Getters and Setters
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}