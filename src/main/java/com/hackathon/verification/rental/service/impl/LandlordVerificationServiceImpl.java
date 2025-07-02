package com.hackathon.verification.rental.service.impl;

import com.hackathon.verification.rental.dto.LandlordVerificationRequest;
import com.hackathon.verification.rental.dto.LandlordVerificationResponse;
import com.hackathon.verification.rental.entity.Landlord;
import com.hackathon.verification.rental.entity.Rating;
import com.hackathon.verification.rental.entity.RentalHistory;
import com.hackathon.verification.rental.repository.LandlordRepository;
import com.hackathon.verification.rental.repository.RatingRepository;
import com.hackathon.verification.rental.repository.RentalHistoryRepository;
import com.hackathon.verification.rental.service.LandlordVerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LandlordVerificationServiceImpl implements LandlordVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(LandlordVerificationServiceImpl.class);

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private RentalHistoryRepository rentalHistoryRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public LandlordVerificationResponse verifyLandlord(LandlordVerificationRequest request) {
        logger.info("Verifying landlord with identifier: {}, type: {}", 
                    request.getIdentifier(), request.getIdentifierType());

        // Find landlord by identifier
        Landlord landlord = findLandlordByIdentifier(request.getIdentifier(), request.getIdentifierType());

        // If landlord not found, return not found response
        if (landlord == null) {
            return createNotFoundResponse(request.getIdentifier());
        }

        // Calculate trust score and determine classification
        Integer trustScore = calculateTrustScore(landlord);
        String classification = determineClassification(landlord, trustScore);

        // Calculate additional scores
        Integer responsivenessScore = calculateResponsivenessScore(landlord);
        Integer fairnessScore = calculateFairnessScore(landlord);
        Double depositReturnRate = calculateDepositReturnRate(landlord);

        // Update landlord with new scores and classification
        landlord = updateLandlordTrustScoreAndClassification(landlord, trustScore, classification);

        // Generate behavioral summary and identify red flags
        String behavioralSummary = generateBehavioralSummary(landlord);
        List<String> redFlags = identifyRedFlags(landlord);

        // Create response with landlord details
        LandlordVerificationResponse response = new LandlordVerificationResponse(
            landlord.getName(),
            landlord.getIdNumber(),
            landlord.getPhone(),
            landlord.getAddress(),
            true,
            landlord.getVerificationStatus(),
            "Landlord verification completed successfully"
        );

        // Set AI-generated assessments
        response.setAverageRating(landlord.getAverageRating());
        response.setTrustScore(trustScore);
        response.setClassification(classification);
        response.setResponsivenessScore(responsivenessScore);
        response.setFairnessScore(fairnessScore);
        response.setDepositReturnRate(depositReturnRate);
        response.setBehavioralSummary(behavioralSummary);
        response.setRedFlags(redFlags);

        // Extract managed properties from the landlord entity
        if (landlord.getManagedProperties() != null && !landlord.getManagedProperties().isEmpty()) {
            List<String> properties = Arrays.asList(landlord.getManagedProperties().split(","));
            response.setManagedProperties(properties);
        }

        // Add property details if requested
        if (Boolean.TRUE.equals(request.getIncludeProperties())) {
            addPropertiesToResponse(landlord, response);
        }

        // Add ratings if requested
        if (Boolean.TRUE.equals(request.getIncludeRatings())) {
            addRatingsToResponse(landlord, response);
        }

        return response;
    }

    @Override
    public Landlord findLandlordByIdentifier(String identifier, String identifierType) {
        if (identifier == null || identifier.trim().isEmpty()) {
            return null;
        }

        if (identifierType == null) {
            identifierType = "ID_NUMBER"; // Default to ID number if not specified
        }

        switch (identifierType.toUpperCase()) {
            case "NAME":
                List<Landlord> landlordsByName = landlordRepository.findByNameContainingIgnoreCase(identifier);
                return landlordsByName.isEmpty() ? null : landlordsByName.get(0);
            case "ID_NUMBER":
                return landlordRepository.findByIdNumber(identifier).orElse(null);
            case "PHONE":
                return landlordRepository.findByPhone(identifier).orElse(null);
            case "ADDRESS":
                List<Landlord> landlordsByAddress = landlordRepository.findByAddressContainingIgnoreCase(identifier);
                return landlordsByAddress.isEmpty() ? null : landlordsByAddress.get(0);
            case "PROPERTY_ADDRESS":
                // Find landlords who manage a property with the given address
                List<Landlord> allLandlords = landlordRepository.findAll();
                return allLandlords.stream()
                        .filter(l -> l.getManagedProperties() != null && 
                                l.getManagedProperties().toLowerCase().contains(identifier.toLowerCase()))
                        .findFirst()
                        .orElse(null);
            default:
                logger.warn("Unsupported identifier type: {}", identifierType);
                return null;
        }
    }

    @Override
    public Integer calculateTrustScore(Landlord landlord) {
        if (landlord == null) {
            return 0;
        }

        // Get rental histories for this landlord
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByLandlord(landlord);

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        // Base score starts at 70 (neutral)
        int score = 70;

        // Adjust score based on rental history
        if (!rentalHistories.isEmpty()) {
            // Count disputes and evictions
            int disputesCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                    .count();

            int evictionsCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                    .count();

            // Deduct points for negative factors
            score -= disputesCount * 5; // -5 points per dispute
            score -= evictionsCount * 10; // -10 points per eviction
        }

        // Adjust score based on ratings
        if (!ratings.isEmpty()) {
            // Calculate average rating value
            double avgRating = ratings.stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(3.0); // Default to neutral (3 out of 5)

            // Adjust score based on average rating (can contribute -20 to +20 points)
            score += (avgRating - 3) * 10; // -20 to +20 points

            // Consider specific rating metrics
            double avgResponsiveness = ratings.stream()
                    .filter(r -> r.getResponsiveness() != null)
                    .mapToDouble(Rating::getResponsiveness)
                    .average()
                    .orElse(3.0);

            double avgMaintenance = ratings.stream()
                    .filter(r -> r.getMaintenanceQuality() != null)
                    .mapToDouble(Rating::getMaintenanceQuality)
                    .average()
                    .orElse(3.0);

            double avgFairness = ratings.stream()
                    .filter(r -> r.getFairness() != null)
                    .mapToDouble(Rating::getFairness)
                    .average()
                    .orElse(3.0);

            double avgDepositHandling = ratings.stream()
                    .filter(r -> r.getDepositHandling() != null)
                    .mapToDouble(Rating::getDepositHandling)
                    .average()
                    .orElse(3.0);

            // Adjust score based on specific metrics (each can contribute -5 to +5 points)
            score += (avgResponsiveness - 3) * 2.5;
            score += (avgMaintenance - 3) * 2.5;
            score += (avgFairness - 3) * 2.5;
            score += (avgDepositHandling - 3) * 2.5;

            // Consider sentiment scores if available
            double avgSentiment = ratings.stream()
                    .filter(r -> r.getSentimentScore() != null)
                    .mapToDouble(Rating::getSentimentScore)
                    .average()
                    .orElse(0.0);

            // Sentiment ranges from -1 to 1, adjust score by up to ±10 points
            score += avgSentiment * 10;
        }

        // Ensure score is within 0-100 range
        return Math.max(0, Math.min(100, score));
    }

    @Override
    public Integer calculateResponsivenessScore(Landlord landlord) {
        if (landlord == null) {
            return 50; // Default neutral score
        }

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        if (ratings.isEmpty()) {
            return 50; // Default neutral score if no ratings
        }

        // Calculate average responsiveness rating (1-5 scale)
        double avgResponsiveness = ratings.stream()
                .filter(r -> r.getResponsiveness() != null)
                .mapToDouble(Rating::getResponsiveness)
                .average()
                .orElse(3.0); // Default to neutral (3 out of 5)

        // Convert to 0-100 scale
        int score = (int) ((avgResponsiveness / 5.0) * 100);

        return Math.max(0, Math.min(100, score));
    }

    @Override
    public Integer calculateFairnessScore(Landlord landlord) {
        if (landlord == null) {
            return 50; // Default neutral score
        }

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        if (ratings.isEmpty()) {
            return 50; // Default neutral score if no ratings
        }

        // Calculate average fairness rating (1-5 scale)
        double avgFairness = ratings.stream()
                .filter(r -> r.getFairness() != null)
                .mapToDouble(Rating::getFairness)
                .average()
                .orElse(3.0); // Default to neutral (3 out of 5)

        // Convert to 0-100 scale
        int score = (int) ((avgFairness / 5.0) * 100);

        return Math.max(0, Math.min(100, score));
    }

    @Override
    public Double calculateDepositReturnRate(Landlord landlord) {
        if (landlord == null) {
            return 0.0;
        }

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        if (ratings.isEmpty()) {
            return 0.0; // Default if no ratings
        }

        // Count ratings with deposit handling score of 4 or 5 (good deposit return)
        long goodDepositHandling = ratings.stream()
                .filter(r -> r.getDepositHandling() != null && r.getDepositHandling() >= 4)
                .count();

        // Calculate percentage
        return (double) goodDepositHandling / ratings.size() * 100.0;
    }

    @Override
    public String determineClassification(Landlord landlord, Integer trustScore) {
        if (landlord == null || trustScore == null) {
            return "Unknown";
        }

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        // Check for automatic "Avoid" conditions
        boolean hasVeryLowRatings = ratings.stream()
                .filter(r -> r.getRatingValue() != null && r.getRatingValue() <= 1.5)
                .count() >= 3; // 3 or more very low ratings

        boolean hasVeryLowDepositHandling = ratings.stream()
                .filter(r -> r.getDepositHandling() != null && r.getDepositHandling() <= 1)
                .count() >= 3; // 3 or more very low deposit handling scores

        if (hasVeryLowRatings || hasVeryLowDepositHandling || trustScore < 40) {
            return "Avoid";
        }

        // Check for "Caution" conditions
        boolean hasLowRatings = ratings.stream()
                .filter(r -> r.getRatingValue() != null && r.getRatingValue() <= 2.5)
                .count() >= 2; // 2 or more low ratings

        boolean hasLowResponsiveness = ratings.stream()
                .filter(r -> r.getResponsiveness() != null && r.getResponsiveness() <= 2)
                .count() >= 2; // 2 or more low responsiveness scores

        boolean hasLowMaintenance = ratings.stream()
                .filter(r -> r.getMaintenanceQuality() != null && r.getMaintenanceQuality() <= 2)
                .count() >= 2; // 2 or more low maintenance quality scores

        if (hasLowRatings || hasLowResponsiveness || hasLowMaintenance || trustScore < 70) {
            return "Caution";
        }

        // Default to "Safe"
        return "Safe";
    }

    @Override
    public String generateBehavioralSummary(Landlord landlord) {
        if (landlord == null) {
            return "No landlord information available.";
        }

        // Get rental histories for this landlord
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByLandlord(landlord);

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        // Build behavioral summary
        StringBuilder summary = new StringBuilder();

        // Add property management insights
        if (landlord.getManagedProperties() != null && !landlord.getManagedProperties().isEmpty()) {
            int propertyCount = landlord.getManagedProperties().split(",").length;
            summary.append("Landlord manages ").append(propertyCount).append(" properties. ");
        }

        // Add rental history insights
        if (!rentalHistories.isEmpty()) {
            int totalRentals = rentalHistories.size();

            int disputesCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                    .count();

            int evictionsCount = (int) rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                    .count();

            summary.append("Has ").append(totalRentals).append(" rental records. ");

            if (disputesCount > 0) {
                summary.append("Has been involved in ").append(disputesCount).append(" dispute(s). ");
            }

            if (evictionsCount > 0) {
                summary.append("Has filed ").append(evictionsCount).append(" eviction(s). ");
            }
        }

        // Add rating insights
        if (!ratings.isEmpty()) {
            double avgRating = ratings.stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(0);

            summary.append("Average rating from tenants is ").append(String.format("%.1f", avgRating)).append("/5. ");

            // Calculate average scores for specific metrics
            if (ratings.stream().anyMatch(r -> r.getResponsiveness() != null)) {
                double avgResponsiveness = ratings.stream()
                        .filter(r -> r.getResponsiveness() != null)
                        .mapToDouble(Rating::getResponsiveness)
                        .average()
                        .getAsDouble();

                summary.append("Responsiveness: ").append(String.format("%.1f", avgResponsiveness)).append("/5. ");
            }

            if (ratings.stream().anyMatch(r -> r.getMaintenanceQuality() != null)) {
                double avgMaintenance = ratings.stream()
                        .filter(r -> r.getMaintenanceQuality() != null)
                        .mapToDouble(Rating::getMaintenanceQuality)
                        .average()
                        .getAsDouble();

                summary.append("Maintenance quality: ").append(String.format("%.1f", avgMaintenance)).append("/5. ");
            }

            if (ratings.stream().anyMatch(r -> r.getFairness() != null)) {
                double avgFairness = ratings.stream()
                        .filter(r -> r.getFairness() != null)
                        .mapToDouble(Rating::getFairness)
                        .average()
                        .getAsDouble();

                summary.append("Fairness: ").append(String.format("%.1f", avgFairness)).append("/5. ");
            }

            if (ratings.stream().anyMatch(r -> r.getDepositHandling() != null)) {
                double avgDepositHandling = ratings.stream()
                        .filter(r -> r.getDepositHandling() != null)
                        .mapToDouble(Rating::getDepositHandling)
                        .average()
                        .getAsDouble();

                summary.append("Deposit handling: ").append(String.format("%.1f", avgDepositHandling)).append("/5. ");
            }

            // Extract traits from reviews if available
            List<String> detectedTraits = ratings.stream()
                    .filter(r -> r.getDetectedTraits() != null && !r.getDetectedTraits().isEmpty())
                    .map(Rating::getDetectedTraits)
                    .flatMap(traits -> Arrays.stream(traits.split(",")))
                    .map(String::trim)
                    .distinct()
                    .collect(Collectors.toList());

            if (!detectedTraits.isEmpty()) {
                summary.append("Commonly mentioned traits: ").append(String.join(", ", detectedTraits)).append(".");
            }
        } else {
            summary.append("No rating information available.");
        }

        return summary.toString();
    }

    @Override
    public List<String> identifyRedFlags(Landlord landlord) {
        List<String> redFlags = new ArrayList<>();

        if (landlord == null) {
            return redFlags;
        }

        // Get rental histories for this landlord
        List<RentalHistory> rentalHistories = rentalHistoryRepository.findByLandlord(landlord);

        // Check for red flags in rental history
        if (!rentalHistories.isEmpty()) {
            // Check for evictions
            long evictionsCount = rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                    .count();

            if (evictionsCount > 0) {
                redFlags.add("Has filed " + evictionsCount + " eviction(s)");
            }

            // Check for disputes
            long disputesCount = rentalHistories.stream()
                    .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                    .count();

            if (disputesCount > 0) {
                redFlags.add("Has been involved in " + disputesCount + " dispute(s)");
            }
        }

        // Get ratings for this landlord
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        // Check for red flags in ratings
        if (!ratings.isEmpty()) {
            // Check for low average rating
            double avgRating = ratings.stream()
                    .mapToDouble(Rating::getRatingValue)
                    .average()
                    .orElse(0);

            if (avgRating <= 2) {
                redFlags.add("Low average rating (" + String.format("%.1f", avgRating) + "/5)");
            }

            // Check for low responsiveness
            double avgResponsiveness = ratings.stream()
                    .filter(r -> r.getResponsiveness() != null)
                    .mapToDouble(Rating::getResponsiveness)
                    .average()
                    .orElse(0);

            if (avgResponsiveness <= 2) {
                redFlags.add("Poor responsiveness to tenant issues");
            }

            // Check for low maintenance quality
            double avgMaintenance = ratings.stream()
                    .filter(r -> r.getMaintenanceQuality() != null)
                    .mapToDouble(Rating::getMaintenanceQuality)
                    .average()
                    .orElse(0);

            if (avgMaintenance <= 2) {
                redFlags.add("Poor maintenance quality");
            }

            // Check for low deposit handling
            double avgDepositHandling = ratings.stream()
                    .filter(r -> r.getDepositHandling() != null)
                    .mapToDouble(Rating::getDepositHandling)
                    .average()
                    .orElse(0);

            if (avgDepositHandling <= 2) {
                redFlags.add("Issues with security deposit returns");
            }

            // Check for negative sentiment in reviews
            double avgSentiment = ratings.stream()
                    .filter(r -> r.getSentimentScore() != null)
                    .mapToDouble(Rating::getSentimentScore)
                    .average()
                    .orElse(0);

            if (avgSentiment < -0.3) {
                redFlags.add("Predominantly negative reviews");
            }
        }

        return redFlags;
    }

    @Override
    public Landlord updateLandlordTrustScoreAndClassification(Landlord landlord, Integer trustScore, String classification) {
        if (landlord == null) {
            return null;
        }

        landlord.setTrustScore(trustScore);
        landlord.setClassification(classification);

        // Update additional scores
        landlord.setResponsivenessScore(calculateResponsivenessScore(landlord));
        landlord.setFairnessScore(calculateFairnessScore(landlord));
        landlord.setDepositReturnRate(calculateDepositReturnRate(landlord));

        return landlordRepository.save(landlord);
    }

    // Helper method to create a "not found" response
    private LandlordVerificationResponse createNotFoundResponse(String identifier) {
        LandlordVerificationResponse response = new LandlordVerificationResponse();
        response.setExists(false);
        response.setVerificationStatus("NOT_FOUND");
        response.setMessage("Landlord with identifier '" + identifier + "' not found");
        return response;
    }

    // Helper method to add property details to the response
    private void addPropertiesToResponse(Landlord landlord, LandlordVerificationResponse response) {
        if (landlord.getManagedProperties() == null || landlord.getManagedProperties().isEmpty()) {
            return;
        }

        String[] propertyAddresses = landlord.getManagedProperties().split(",");

        for (String address : propertyAddresses) {
            String trimmedAddress = address.trim();

            // Create property DTO
            LandlordVerificationResponse.PropertyDTO propertyDTO = new LandlordVerificationResponse.PropertyDTO();
            propertyDTO.setPropertyAddress(trimmedAddress);

            // Find rental histories for this property
            List<RentalHistory> allHistories = rentalHistoryRepository.findByLandlord(landlord);
            List<RentalHistory> propertyHistories = allHistories.stream()
                    .filter(rh -> rh.getPropertyAddress() != null && 
                            rh.getPropertyAddress().toLowerCase().contains(trimmedAddress.toLowerCase()))
                    .collect(Collectors.toList());

            if (!propertyHistories.isEmpty()) {
                // Find earliest managed date
                LocalDate earliestDate = propertyHistories.stream()
                        .map(RentalHistory::getLeaseStartDate)
                        .filter(date -> date != null)
                        .min((date1, date2) -> date1.compareTo(date2))
                        .orElse(null);

                propertyDTO.setManagedSince(earliestDate);

                // Count unique tenants
                int tenantCount = (int) propertyHistories.stream()
                        .map(rh -> rh.getTenant().getId())
                        .distinct()
                        .count();

                propertyDTO.setTotalTenants(tenantCount);

                // Count disputes and evictions
                int disputesCount = (int) propertyHistories.stream()
                        .filter(rh -> Boolean.TRUE.equals(rh.getHadDisputes()))
                        .count();

                int evictionsCount = (int) propertyHistories.stream()
                        .filter(rh -> Boolean.TRUE.equals(rh.getEvictionFiled()))
                        .count();

                propertyDTO.setTotalDisputes(disputesCount);
                propertyDTO.setTotalEvictions(evictionsCount);

                // Add tenant names
                List<String> tenantNames = propertyHistories.stream()
                        .map(rh -> rh.getTenant().getName())
                        .distinct()
                        .collect(Collectors.toList());

                propertyDTO.setTenantNames(tenantNames);
            }

            // Find ratings for this property
            List<Rating> propertyRatings = ratingRepository.findByPropertyAddressContainingIgnoreCase(trimmedAddress);

            if (!propertyRatings.isEmpty()) {
                // Calculate average rating
                double avgRating = propertyRatings.stream()
                        .mapToDouble(Rating::getRatingValue)
                        .average()
                        .orElse(0);

                propertyDTO.setAverageRating(avgRating);
            }

            response.addProperty(propertyDTO);
        }
    }

    // Helper method to add ratings to the response
    private void addRatingsToResponse(Landlord landlord, LandlordVerificationResponse response) {
        List<Rating> ratings = ratingRepository.findByLandlordAndRatingType(landlord, "TENANT_TO_LANDLORD");

        for (Rating rating : ratings) {
            LandlordVerificationResponse.RatingDTO ratingDTO = new LandlordVerificationResponse.RatingDTO();
            ratingDTO.setTenantName(rating.getTenant().getName());
            ratingDTO.setRatingValue(rating.getRatingValue());
            ratingDTO.setReview(rating.getReview());
            ratingDTO.setPropertyAddress(rating.getPropertyAddress());
            ratingDTO.setRatingDate(rating.getCreatedAt() != null ? rating.getCreatedAt().toLocalDate() : null);

            // Add specific metrics if available
            if (rating.getResponsiveness() != null) {
                ratingDTO.setResponsiveness(rating.getResponsiveness());
            }
            if (rating.getMaintenanceQuality() != null) {
                ratingDTO.setMaintenanceQuality(rating.getMaintenanceQuality());
            }
            if (rating.getFairness() != null) {
                ratingDTO.setFairness(rating.getFairness());
            }
            if (rating.getDepositHandling() != null) {
                ratingDTO.setDepositHandling(rating.getDepositHandling());
            }
            if (rating.getPrivacyRespect() != null) {
                ratingDTO.setPrivacyRespect(rating.getPrivacyRespect());
            }

            // Add detected traits if available
            if (rating.getDetectedTraits() != null && !rating.getDetectedTraits().isEmpty()) {
                List<String> traits = Arrays.stream(rating.getDetectedTraits().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                ratingDTO.setDetectedTraits(traits);
            }

            response.addRating(ratingDTO);
        }
    }
}
