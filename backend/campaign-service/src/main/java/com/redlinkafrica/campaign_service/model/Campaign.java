package com.redlinkafrica.campaign_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Summer Blood Drive 2025"

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private int targetDonors; // Target number of donors

    @Column(nullable = false)
    private String status; // e.g., "PLANNED", "ACTIVE", "COMPLETED"

    @ElementCollection
    @CollectionTable(name = "campaign_donors", joinColumns = @JoinColumn(name = "campaign_id"))
    private List<Long> donorIds; // List of donor IDs who participated

    // Getters, setters, constructors (omitted for brevity, add as needed)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public String getTown() { return town; }
    public void setTown(String town) { this.town = town; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public int getTargetDonors() { return targetDonors; }
    public void setTargetDonors(int targetDonors) { this.targetDonors = targetDonors; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Long> getDonorIds() { return donorIds; }
    public void setDonorIds(List<Long> donorIds) { this.donorIds = donorIds; }
}