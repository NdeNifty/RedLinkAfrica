package com.redlinkafrica.donor_service.model;

import java.time.LocalDateTime;

public class RequestDTO {
    private Long id;
    private String hospitalId;
    private String bloodType;
    private int quantity;
    private String urgency; // String to match JSON, or use enum if needed
    private LocalDateTime requestDate;
    private String crossBorderId;
    private String status;
    private Long donorId;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHospitalId() { return hospitalId; }
    public void setHospitalId(String hospitalId) { this.hospitalId = hospitalId; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public String getCrossBorderId() { return crossBorderId; }
    public void setCrossBorderId(String crossBorderId) { this.crossBorderId = crossBorderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }
}