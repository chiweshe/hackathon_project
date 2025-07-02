package com.hackathon.verification.exception;

/**
 * Custom exception for verification-related errors.
 */
public class VerificationException extends RuntimeException {
    
    private final String entityType;
    private final String identifier;
    private final String errorCode;
    
    /**
     * Constructs a new verification exception with the specified detail message.
     *
     * @param message the detail message
     * @param entityType the type of entity being verified (e.g., "land", "vehicle", "landlord", "tenant")
     * @param identifier the identifier of the entity (e.g., stand number, chassis number, ID number)
     * @param errorCode a code identifying the type of error
     */
    public VerificationException(String message, String entityType, String identifier, String errorCode) {
        super(message);
        this.entityType = entityType;
        this.identifier = identifier;
        this.errorCode = errorCode;
    }
    
    /**
     * Constructs a new verification exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     * @param entityType the type of entity being verified
     * @param identifier the identifier of the entity
     * @param errorCode a code identifying the type of error
     */
    public VerificationException(String message, Throwable cause, String entityType, String identifier, String errorCode) {
        super(message, cause);
        this.entityType = entityType;
        this.identifier = identifier;
        this.errorCode = errorCode;
    }
    
    /**
     * Gets the type of entity being verified.
     *
     * @return the entity type
     */
    public String getEntityType() {
        return entityType;
    }
    
    /**
     * Gets the identifier of the entity.
     *
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Factory method for creating a "not found" exception.
     *
     * @param entityType the type of entity
     * @param identifier the identifier of the entity
     * @return a new VerificationException
     */
    public static VerificationException notFound(String entityType, String identifier) {
        return new VerificationException(
                entityType + " not found with identifier: " + identifier,
                entityType,
                identifier,
                "NOT_FOUND"
        );
    }
    
    /**
     * Factory method for creating an "already exists" exception.
     *
     * @param entityType the type of entity
     * @param identifier the identifier of the entity
     * @return a new VerificationException
     */
    public static VerificationException alreadyExists(String entityType, String identifier) {
        return new VerificationException(
                entityType + " with identifier " + identifier + " already exists",
                entityType,
                identifier,
                "ALREADY_EXISTS"
        );
    }
    
    /**
     * Factory method for creating an "invalid input" exception.
     *
     * @param entityType the type of entity
     * @param identifier the identifier of the entity
     * @param details details about the invalid input
     * @return a new VerificationException
     */
    public static VerificationException invalidInput(String entityType, String identifier, String details) {
        return new VerificationException(
                "Invalid input for " + entityType + " with identifier " + identifier + ": " + details,
                entityType,
                identifier,
                "INVALID_INPUT"
        );
    }
    
    /**
     * Factory method for creating a "verification failed" exception.
     *
     * @param entityType the type of entity
     * @param identifier the identifier of the entity
     * @param details details about the verification failure
     * @return a new VerificationException
     */
    public static VerificationException verificationFailed(String entityType, String identifier, String details) {
        return new VerificationException(
                "Verification failed for " + entityType + " with identifier " + identifier + ": " + details,
                entityType,
                identifier,
                "VERIFICATION_FAILED"
        );
    }
}