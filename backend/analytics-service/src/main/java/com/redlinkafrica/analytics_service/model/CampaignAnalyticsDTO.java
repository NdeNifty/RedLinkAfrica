package com.redlinkafrica.analytics_service.model;

import java.util.Map;

public class CampaignAnalyticsDTO {
    private int totalCampaigns;
    private double successRate;
    private Map<String, Integer> campaignsByLocation;
    private Map<String, Integer> campaignsByStatus;

    // Getters and setters
    public int getTotalCampaigns() { return totalCampaigns; }
    public void setTotalCampaigns(int totalCampaigns) { this.totalCampaigns = totalCampaigns; }
    public double getSuccessRate() { return successRate; }
    public void setSuccessRate(double successRate) { this.successRate = successRate; }
    public Map<String, Integer> getCampaignsByLocation() { return campaignsByLocation; }
    public void setCampaignsByLocation(Map<String, Integer> campaignsByLocation) { this.campaignsByLocation = campaignsByLocation; }
    public Map<String, Integer> getCampaignsByStatus() { return campaignsByStatus; }
    public void setCampaignsByStatus(Map<String, Integer> campaignsByStatus) { this.campaignsByStatus = campaignsByStatus; }
}