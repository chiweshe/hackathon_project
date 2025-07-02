package com.hackathon.verification.rental.controller;

import com.hackathon.verification.rental.dto.RatingDTO;
import com.hackathon.verification.rental.entity.Rating;
import com.hackathon.verification.rental.service.RatingService;
import com.hackathon.verification.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ratings")
@Tag(name = "Rating API", description = "API for managing ratings between landlords and tenants")
public class RatingController {

    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    @PostMapping
    @Operation(summary = "Create a new rating", description = "Creates a new rating from a landlord to a tenant or vice versa")
    public ResponseEntity<Rating> createRating(@Valid @RequestBody RatingDTO ratingDTO) {
        logger.info("Creating new rating from {} to {}", 
                   ratingDTO.getRatingType().equals("LANDLORD_TO_TENANT") ? "landlord" : "tenant",
                   ratingDTO.getRatingType().equals("LANDLORD_TO_TENANT") ? "tenant" : "landlord");

        Rating rating = ratingService.createRating(ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get rating by ID", description = "Retrieves a rating by its ID")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        logger.info("Retrieving rating with ID: {}", id);

        Rating rating = ratingService.getRatingById(id);
        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rating);
    }

    @GetMapping("/landlord/{landlordId}")
    @Operation(summary = "Get ratings by landlord", description = "Retrieves all ratings for a landlord")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingsByLandlord(@PathVariable Long landlordId) {
        logger.info("Retrieving ratings for landlord with ID: {}", landlordId);

        List<Rating> ratings = ratingService.getRatingsByLandlord(landlordId);
        if (ratings.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.emptyList("No ratings found for landlord with ID: " + landlordId));
        }
        return ResponseEntity.ok(new ApiResponse<>(ratings));
    }

    @GetMapping("/tenant/{tenantId}")
    @Operation(summary = "Get ratings by tenant", description = "Retrieves all ratings for a tenant")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingsByTenant(@PathVariable Long tenantId) {
        logger.info("Retrieving ratings for tenant with ID: {}", tenantId);

        List<Rating> ratings = ratingService.getRatingsByTenant(tenantId);
        if (ratings.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.emptyList("No ratings found for tenant with ID: " + tenantId));
        }
        return ResponseEntity.ok(new ApiResponse<>(ratings));
    }

    @GetMapping("/property")
    @Operation(summary = "Get ratings by property", description = "Retrieves all ratings for a property")
    public ResponseEntity<ApiResponse<List<Rating>>> getRatingsByProperty(@RequestParam String propertyAddress) {
        logger.info("Retrieving ratings for property: {}", propertyAddress);

        List<Rating> ratings = ratingService.getRatingsByProperty(propertyAddress);
        if (ratings.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.emptyList("No ratings found for property address: " + propertyAddress));
        }
        return ResponseEntity.ok(new ApiResponse<>(ratings));
    }

    @GetMapping("/landlord/{landlordId}/to-tenants")
    @Operation(summary = "Get ratings from landlord to tenants", description = "Retrieves all ratings from a landlord to tenants")
    public ResponseEntity<ApiResponse<List<Rating>>> getLandlordToTenantRatings(@PathVariable Long landlordId) {
        logger.info("Retrieving ratings from landlord with ID: {} to tenants", landlordId);

        List<Rating> ratings = ratingService.getLandlordToTenantRatings(landlordId);
        if (ratings.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.emptyList("No ratings found from landlord with ID: " + landlordId + " to tenants"));
        }
        return ResponseEntity.ok(new ApiResponse<>(ratings));
    }

    @GetMapping("/landlord/{landlordId}/from-tenants")
    @Operation(summary = "Get ratings from tenants to landlord", description = "Retrieves all ratings from tenants to a landlord")
    public ResponseEntity<ApiResponse<List<Rating>>> getTenantToLandlordRatings(@PathVariable Long landlordId) {
        logger.info("Retrieving ratings from tenants to landlord with ID: {}", landlordId);

        List<Rating> ratings = ratingService.getTenantToLandlordRatings(landlordId);
        if (ratings.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.emptyList("No ratings found from tenants to landlord with ID: " + landlordId));
        }
        return ResponseEntity.ok(new ApiResponse<>(ratings));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update rating", description = "Updates an existing rating")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @Valid @RequestBody RatingDTO ratingDTO) {
        logger.info("Updating rating with ID: {}", id);

        try {
            Rating updatedRating = ratingService.updateRating(id, ratingDTO);
            return ResponseEntity.ok(updatedRating);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating rating: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rating", description = "Deletes a rating by its ID")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        logger.info("Deleting rating with ID: {}", id);

        Rating rating = ratingService.getRatingById(id);
        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/analyze-sentiment")
    @Operation(summary = "Analyze sentiment", description = "Analyzes sentiment in a review text")
    public ResponseEntity<Map<String, Object>> analyzeSentiment(@RequestBody Map<String, String> request) {
        String reviewText = request.get("reviewText");
        if (reviewText == null || reviewText.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        logger.info("Analyzing sentiment in review text");

        Double sentimentScore = ratingService.analyzeSentiment(reviewText);
        String traits = ratingService.extractTraits(reviewText);

        Map<String, Object> response = Map.of(
            "sentimentScore", sentimentScore,
            "detectedTraits", traits
        );

        return ResponseEntity.ok(response);
    }
}
