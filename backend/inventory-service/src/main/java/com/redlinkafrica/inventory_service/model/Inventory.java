package com.redlinkafrica.inventory_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bloodBankId; // Reference to blood bank

    @Column(nullable = false)
    private String bloodType; // e.g., "O+", "A-"

    @Column(nullable = false)
    private int volume; // Units of blood

    @Column(nullable = false)
    private LocalDate expiryDate; // Expiration date

    @Column(nullable = false)
    private Long donatedBy; // ID of the donor from donors table

    @Column(nullable = false)
    private LocalDate donationDate; // Date of donation

    private String crossBorderId; // For cross-border inventory tracking
    private String status; // e.g., "AVAILABLE", "RESERVED"

    // Getters, setters, constructors
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBloodBankId() { return bloodBankId; }
    public void setBloodBankId(String bloodBankId) { this.bloodBankId = bloodBankId; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public Long getDonatedBy() { return donatedBy; }
    public void setDonatedBy(Long donatedBy) { this.donatedBy = donatedBy; }
    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }
    public String getCrossBorderId() { return crossBorderId; }
    public void setCrossBorderId(String crossBorderId) { this.crossBorderId = crossBorderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}