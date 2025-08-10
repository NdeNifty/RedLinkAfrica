package com.redlinkafrica.analytics_service.model;

import java.util.Map;

public class InventoryAnalyticsDTO {
    private int totalVolume;
    private Map<String, Integer> volumeByBloodType;
    private Map<String, Integer> volumeByLocation;
    private int nearingExpiry;

    // Getters and setters
    public int getTotalVolume() { return totalVolume; }
    public void setTotalVolume(int totalVolume) { this.totalVolume = totalVolume; }
    public Map<String, Integer> getVolumeByBloodType() { return volumeByBloodType; }
    public void setVolumeByBloodType(Map<String, Integer> volumeByBloodType) { this.volumeByBloodType = volumeByBloodType; }
    public Map<String, Integer> getVolumeByLocation() { return volumeByLocation; }
    public void setVolumeByLocation(Map<String, Integer> volumeByLocation) { this.volumeByLocation = volumeByLocation; }
    public int getNearingExpiry() { return nearingExpiry; }
    public void setNearingExpiry(int nearingExpiry) { this.nearingExpiry = nearingExpiry; }
}