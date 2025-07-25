package com.redlinkafrica.campaign_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location; // e.g., "Yaoundé, Cameroon"

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String bloodTypeNeeds; // e.g., "O+, A-"

    @Column(nullable = false)
    private int capacity; // Max number of donors

    private String crossBorderId; // For cross-border campaigns
    private int registeredDonors; // Track RSVPs

    // Getters, setters, constructors
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getBloodTypeNeeds() { return bloodTypeNeeds; }
    public void setBloodTypeNeeds(String bloodTypeNeeds) { this.bloodTypeNeeds = bloodTypeNeeds; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getCrossBorderId() { return crossBorderId; }
    public void setCrossBorderId(String crossBorderId) { this.crossBorderId = crossBorderId; }
    public int getRegisteredDonors() { return registeredDonors; }
    public void setRegisteredDonors(int registeredDonors) { this.registeredDonors = registeredDonors; }
}