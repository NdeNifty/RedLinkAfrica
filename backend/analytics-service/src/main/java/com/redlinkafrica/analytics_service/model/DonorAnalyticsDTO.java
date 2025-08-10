package com.redlinkafrica.analytics_service.model;

import java.util.Map;

public class DonorAnalyticsDTO {
    private int totalDonors;
    private Map<String, Integer> donorsByBloodType;
    private Map<String, Integer> donorsByLocation;

    // Getters and setters
    public int getTotalDonors() { return totalDonors; }
    public void setTotalDonors(int totalDonors) { this.totalDonors = totalDonors; }
    public Map<String, Integer> getDonorsByBloodType() { return donorsByBloodType; }
    public void setDonorsByBloodType(Map<String, Integer> donorsByBloodType) { this.donorsByBloodType = donorsByBloodType; }
    public Map<String, Integer> getDonorsByLocation() { return donorsByLocation; }
    public void setDonorsByLocation(Map<String, Integer> donorsByLocation) { this.donorsByLocation = donorsByLocation; }
}