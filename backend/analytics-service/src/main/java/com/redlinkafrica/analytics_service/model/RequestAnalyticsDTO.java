package com.redlinkafrica.analytics_service.model;

import java.util.Map;

public class RequestAnalyticsDTO {
    private int totalRequests;
    private Map<String, Integer> requestsByBloodType;
    private Map<String, Integer> requestsByUrgency;
    private int fulfilledRequests;
    private int pendingRequests;

    // Getters and setters
    public int getTotalRequests() { return totalRequests; }
    public void setTotalRequests(int totalRequests) { this.totalRequests = totalRequests; }
    public Map<String, Integer> getRequestsByBloodType() { return requestsByBloodType; }
    public void setRequestsByBloodType(Map<String, Integer> requestsByBloodType) { this.requestsByBloodType = requestsByBloodType; }
    public Map<String, Integer> getRequestsByUrgency() { return requestsByUrgency; }
    public void setRequestsByUrgency(Map<String, Integer> requestsByUrgency) { this.requestsByUrgency = requestsByUrgency; }
    public int getFulfilledRequests() { return fulfilledRequests; }
    public void setFulfilledRequests(int fulfilledRequests) { this.fulfilledRequests = fulfilledRequests; }
    public int getPendingRequests() { return pendingRequests; }
    public void setPendingRequests(int pendingRequests) { this.pendingRequests = pendingRequests; }
}