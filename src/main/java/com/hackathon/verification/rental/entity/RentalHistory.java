package com.hackathon.verification.rental.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_histories")
public class RentalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "landlord_id", nullable = false)
    private Landlord landlord;

    @Column(name = "property_address", nullable = false)
    private String propertyAddress;

    @Column(name = "lease_start_date", nullable = false)
    private LocalDate leaseStartDate;

    @Column(name = "lease_end_date")
    private LocalDate leaseEndDate;

    @Column(name = "rent_amount", nullable = false)
    private Double rentAmount;

    @Column(name = "on_time_payments")
    private Boolean onTimePayments;

    @Column(name = "late_payments_count")
    private Integer latePaymentsCount;

    @Column(name = "property_damage")
    private Boolean propertyDamage;

    @Column(name = "damage_description", columnDefinition = "TEXT")
    private String damageDescription;

    @Column(name = "security_deposit_returned")
    private Boolean securityDepositReturned;

    @Column(name = "deposit_amount")
    private Double depositAmount;

    @Column(name = "deposit_deduction_reason", columnDefinition = "TEXT")
    private String depositDeductionReason;

    @Column(name = "had_disputes")
    private Boolean hadDisputes;

    @Column(name = "dispute_description", columnDefinition = "TEXT")
    private String disputeDescription;

    @Column(name = "eviction_filed")
    private Boolean evictionFiled;

    @Column(name = "eviction_reason", columnDefinition = "TEXT")
    private String evictionReason;

    @Column(name = "landlord_responsiveness_rating")
    private Integer landlordResponsivenessRating; // 1-5 scale

    @Column(name = "landlord_fairness_rating")
    private Integer landlordFairnessRating; // 1-5 scale

    @Column(name = "tenant_cleanliness_rating")
    private Integer tenantCleanlinessRating; // 1-5 scale

    @Column(name = "tenant_cooperation_rating")
    private Integer tenantCooperationRating; // 1-5 scale

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (latePaymentsCount == null) latePaymentsCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Default constructor
    public RentalHistory() {
    }

    // Constructor with essential fields
    public RentalHistory(Tenant tenant, Landlord landlord, String propertyAddress, 
                        LocalDate leaseStartDate, LocalDate leaseEndDate, Double rentAmount) {
        this.tenant = tenant;
        this.landlord = landlord;
        this.propertyAddress = propertyAddress;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.rentAmount = rentAmount;
        this.latePaymentsCount = 0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public Double getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(Double rentAmount) {
        this.rentAmount = rentAmount;
    }

    public Boolean getOnTimePayments() {
        return onTimePayments;
    }

    public void setOnTimePayments(Boolean onTimePayments) {
        this.onTimePayments = onTimePayments;
    }

    public Integer getLatePaymentsCount() {
        return latePaymentsCount;
    }

    public void setLatePaymentsCount(Integer latePaymentsCount) {
        this.latePaymentsCount = latePaymentsCount;
    }

    public Boolean getPropertyDamage() {
        return propertyDamage;
    }

    public void setPropertyDamage(Boolean propertyDamage) {
        this.propertyDamage = propertyDamage;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public Boolean getSecurityDepositReturned() {
        return securityDepositReturned;
    }

    public void setSecurityDepositReturned(Boolean securityDepositReturned) {
        this.securityDepositReturned = securityDepositReturned;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositDeductionReason() {
        return depositDeductionReason;
    }

    public void setDepositDeductionReason(String depositDeductionReason) {
        this.depositDeductionReason = depositDeductionReason;
    }

    public Boolean getHadDisputes() {
        return hadDisputes;
    }

    public void setHadDisputes(Boolean hadDisputes) {
        this.hadDisputes = hadDisputes;
    }

    public String getDisputeDescription() {
        return disputeDescription;
    }

    public void setDisputeDescription(String disputeDescription) {
        this.disputeDescription = disputeDescription;
    }

    public Boolean getEvictionFiled() {
        return evictionFiled;
    }

    public void setEvictionFiled(Boolean evictionFiled) {
        this.evictionFiled = evictionFiled;
    }

    public String getEvictionReason() {
        return evictionReason;
    }

    public void setEvictionReason(String evictionReason) {
        this.evictionReason = evictionReason;
    }

    public Integer getLandlordResponsivenessRating() {
        return landlordResponsivenessRating;
    }

    public void setLandlordResponsivenessRating(Integer landlordResponsivenessRating) {
        this.landlordResponsivenessRating = landlordResponsivenessRating;
    }

    public Integer getLandlordFairnessRating() {
        return landlordFairnessRating;
    }

    public void setLandlordFairnessRating(Integer landlordFairnessRating) {
        this.landlordFairnessRating = landlordFairnessRating;
    }

    public Integer getTenantCleanlinessRating() {
        return tenantCleanlinessRating;
    }

    public void setTenantCleanlinessRating(Integer tenantCleanlinessRating) {
        this.tenantCleanlinessRating = tenantCleanlinessRating;
    }

    public Integer getTenantCooperationRating() {
        return tenantCooperationRating;
    }

    public void setTenantCooperationRating(Integer tenantCooperationRating) {
        this.tenantCooperationRating = tenantCooperationRating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "RentalHistory{" +
                "id=" + id +
                ", tenant=" + (tenant != null ? tenant.getId() : null) +
                ", landlord=" + (landlord != null ? landlord.getId() : null) +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", leaseStartDate=" + leaseStartDate +
                ", leaseEndDate=" + leaseEndDate +
                ", rentAmount=" + rentAmount +
                ", onTimePayments=" + onTimePayments +
                ", latePaymentsCount=" + latePaymentsCount +
                ", propertyDamage=" + propertyDamage +
                ", hadDisputes=" + hadDisputes +
                ", evictionFiled=" + evictionFiled +
                '}';
    }
}