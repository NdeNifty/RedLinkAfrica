package com.redlinkafrica.donor_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "donors")
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String country;

    private String bloodType; // e.g., "O+", "A-"
    private boolean eligible; // Based on donation eligibility rules
    private String crossBorderId; // Unique ID for cross-border tracking

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // e.g., "DONOR", "ADMIN"

    // Getters, setters, constructors
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getTown() { return town; }
    public void setTown(String town) { this.town = town; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public boolean isEligible() { return eligible; }
    public void setEligible(boolean eligible) { this.eligible = eligible; }
    public String getCrossBorderId() { return crossBorderId; }
    public void setCrossBorderId(String crossBorderId) { this.crossBorderId = crossBorderId; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public enum Role {
        DONOR, ADMIN
    }
}