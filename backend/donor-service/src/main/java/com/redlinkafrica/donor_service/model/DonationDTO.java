package com.redlinkafrica.donor_service.model;

import java.time.LocalDate;

public class DonationDTO {
    private Long id;
    private String bloodBankId;
    private String bloodType;
    private int volume;
    private LocalDate expiryDate;
    private Long donatedBy;
    private LocalDate donationDate;
    private String town;
    private String country;
    private String hospitalId;
    private String crossBorderId;
    private String status;

    // Getters and setters
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
    public String getTown() { return town; }
    public void setTown(String town) { this.town = town; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getHospitalId() { return hospitalId; }
    public void setHospitalId(String hospitalId) { this.hospitalId = hospitalId; }
    public String getCrossBorderId() { return crossBorderId; }
    public void setCrossBorderId(String crossBorderId) { this.crossBorderId = crossBorderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}