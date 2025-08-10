package com.redlinkafrica.request_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hospitalId; // Reference to hospital

    @Column(nullable = false)
    private String bloodType; // e.g., "O+", "A-"

    @Column(nullable = false)
    private int quantity; // Units requested

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Urgency urgency; // e.g., "LOW", "HIGH", "EMERGENCY"

    private LocalDateTime requestDate;
    private String crossBorderId; // For cross-border requests
    private String status; // e.g., "PENDING", "FULFILLED"

    @Column(name = "donor_id") // Link to donor who made the request
    private Long donorId;

    // Getters, setters, constructors (omitted for brevity, add as needed)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHospitalId() { return hospitalId; }
    public void setHospitalId(String hospitalId) { this.hospitalId = hospitalId; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Urgency getUrgency() { return urgency; }
    public void setUrgency(Urgency urgency) { this.urgency = urgency; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public String getCrossBorderId() { return crossBorderId; }
    public void setCrossBorderId(String crossBorderId) { this.crossBorderId = crossBorderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getDonorId() { return donorId; }
    public void setDonorId(Long donorId) { this.donorId = donorId; }

    public enum Urgency {
        LOW, HIGH, EMERGENCY
    }
}