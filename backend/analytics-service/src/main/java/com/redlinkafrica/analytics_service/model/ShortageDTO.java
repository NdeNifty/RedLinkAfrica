package com.redlinkafrica.analytics_service.model;

import java.util.Map;

public class ShortageDTO {
    private Map<String, Map<String, Integer>> shortagesByLocation; // Map of country -> (town -> shortage quantity)

    // Getters and setters
    public Map<String, Map<String, Integer>> getShortagesByLocation() { return shortagesByLocation; }
    public void setShortagesByLocation(Map<String, Map<String, Integer>> shortagesByLocation) { this.shortagesByLocation = shortagesByLocation; }
}