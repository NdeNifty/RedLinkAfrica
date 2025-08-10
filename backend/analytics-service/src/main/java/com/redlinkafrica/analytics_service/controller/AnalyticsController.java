package com.redlinkafrica.analytics_service.controller;

import com.redlinkafrica.analytics_service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DONOR_SERVICE_URL = "http://localhost:8080/api/donors";
    private static final String INVENTORY_SERVICE_URL = "http://localhost:8081/api/inventory";
    private static final String REQUEST_SERVICE_URL = "http://localhost:8082/api/requests";
    private static final String CAMPAIGN_SERVICE_URL = "http://localhost:8083/api/campaigns";

    // GET donor analytics
    @GetMapping("/donors")
    public ResponseEntity<DonorAnalyticsDTO> getDonorAnalytics() {
        Donor[] donors = restTemplate.getForObject(DONOR_SERVICE_URL, Donor[].class);
        if (donors == null) return ResponseEntity.notFound().build();

        DonorAnalyticsDTO dto = new DonorAnalyticsDTO();
        dto.setTotalDonors(donors.length);

        Map<String, Integer> byBloodType = Arrays.stream(donors)
                .collect(Collectors.groupingBy(Donor::getBloodType, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        dto.setDonorsByBloodType(byBloodType);

        Map<String, Integer> byLocation = Arrays.stream(donors)
                .collect(Collectors.groupingBy(d -> d.getTown() + ", " + d.getCountry(), Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        dto.setDonorsByLocation(byLocation);

        return ResponseEntity.ok(dto);
    }

    // GET inventory analytics
    @GetMapping("/inventory")
    public ResponseEntity<InventoryAnalyticsDTO> getInventoryAnalytics() {
        Inventory[] inventory = restTemplate.getForObject(INVENTORY_SERVICE_URL, Inventory[].class);
        if (inventory == null) return ResponseEntity.notFound().build();

        InventoryAnalyticsDTO dto = new InventoryAnalyticsDTO();
        dto.setTotalVolume(Arrays.stream(inventory).mapToInt(Inventory::getVolume).sum());

        Map<String, Integer> byBloodType = Arrays.stream(inventory)
                .collect(Collectors.groupingBy(Inventory::getBloodType, Collectors.summingInt(Inventory::getVolume)));
        dto.setVolumeByBloodType(byBloodType);

        Map<String, Integer> byLocation = Arrays.stream(inventory)
                .collect(Collectors.groupingBy(i -> i.getTown() + ", " + i.getCountry(), Collectors.summingInt(Inventory::getVolume)));
        dto.setVolumeByLocation(byLocation);

        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        dto.setNearingExpiry((int) Arrays.stream(inventory)
                .filter(i -> i.getExpiryDate().isBefore(thirtyDaysFromNow))
                .mapToInt(Inventory::getVolume).sum());

        return ResponseEntity.ok(dto);
    }

    // GET request analytics
    @GetMapping("/requests")
    public ResponseEntity<RequestAnalyticsDTO> getRequestAnalytics() {
        Request[] requests = restTemplate.getForObject(REQUEST_SERVICE_URL, Request[].class);
        if (requests == null) return ResponseEntity.notFound().build();

        RequestAnalyticsDTO dto = new RequestAnalyticsDTO();
        dto.setTotalRequests(requests.length);

        Map<String, Integer> byBloodType = Arrays.stream(requests)
                .collect(Collectors.groupingBy(Request::getBloodType, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        dto.setRequestsByBloodType(byBloodType);

        Map<String, Integer> byUrgency = Arrays.stream(requests)
                .collect(Collectors.groupingBy(Request::getUrgency, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        dto.setRequestsByUrgency(byUrgency);

        dto.setFulfilledRequests((int) Arrays.stream(requests)
                .filter(r -> "FULFILLED".equalsIgnoreCase(r.getStatus()))
                .count());
        dto.setPendingRequests((int) Arrays.stream(requests)
                .filter(r -> "PENDING".equalsIgnoreCase(r.getStatus()))
                .count());

        return ResponseEntity.ok(dto);
    }

    // GET campaign analytics
    @GetMapping("/campaigns")
    public ResponseEntity<CampaignAnalyticsDTO> getCampaignAnalytics() {
        Campaign[] campaigns = restTemplate.getForObject(CAMPAIGN_SERVICE_URL, Campaign[].class);
        if (campaigns == null) return ResponseEntity.notFound().build();

        CampaignAnalyticsDTO dto = new CampaignAnalyticsDTO();
        dto.setTotalCampaigns(campaigns.length);

        long successful = Arrays.stream(campaigns)
                .filter(c -> c.getDonorIds().size() >= c.getTargetDonors())
                .count();
        dto.setSuccessRate(campaigns.length > 0 ? (successful * 100.0) / campaigns.length : 0.0);

        Map<String, Integer> byLocation = Arrays.stream(campaigns)
                .collect(Collectors.groupingBy(c -> c.getTown() + ", " + c.getCountry(), Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        dto.setCampaignsByLocation(byLocation);

        Map<String, Integer> byStatus = Arrays.stream(campaigns)
                .collect(Collectors.groupingBy(Campaign::getStatus, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        dto.setCampaignsByStatus(byStatus);

        return ResponseEntity.ok(dto);
    }

    // GET blood type shortages by location
    @GetMapping("/shortages")
    public ResponseEntity<ShortageDTO> getBloodTypeShortages() {
        Inventory[] inventory = restTemplate.getForObject(INVENTORY_SERVICE_URL, Inventory[].class);
        Request[] requests = restTemplate.getForObject(REQUEST_SERVICE_URL, Request[].class);
        if (inventory == null || requests == null) return ResponseEntity.notFound().build();

        Map<String, Map<String, Integer>> shortages = new HashMap<>();
        Map<String, Map<String, Integer>> inventoryByLocation = new HashMap<>();
        Map<String, Map<String, Integer>> requestsByLocation = new HashMap<>();

        // Aggregate inventory by location and blood type
        for (Inventory inv : inventory) {
            String location = inv.getTown() + ", " + inv.getCountry();
            inventoryByLocation.computeIfAbsent(location, k -> new HashMap<>())
                    .merge(inv.getBloodType(), inv.getVolume(), Integer::sum);
        }

        // Aggregate requests by location and blood type
        for (Request req : requests) {
            String location = req.getTown() + ", " + req.getCountry(); // Assuming Request has town/country (add if missing)
            requestsByLocation.computeIfAbsent(location, k -> new HashMap<>())
                    .merge(req.getBloodType(), req.getQuantity(), Integer::sum);
        }

        // Calculate shortages
        for (String location : inventoryByLocation.keySet()) {
            Map<String, Integer> invMap = inventoryByLocation.getOrDefault(location, new HashMap<>());
            Map<String, Integer> reqMap = requestsByLocation.getOrDefault(location, new HashMap<>());
            Map<String, Integer> shortageMap = new HashMap<>();
            for (String bloodType : reqMap.keySet()) {
                int invQty = invMap.getOrDefault(bloodType, 0);
                int reqQty = reqMap.getOrDefault(bloodType, 0);
                if (invQty < reqQty) {
                    shortageMap.put(bloodType, reqQty - invQty);
                }
            }
            if (!shortageMap.isEmpty()) {
                shortages.put(location, shortageMap);
            }
        }

        ShortageDTO dto = new ShortageDTO();
        dto.setShortagesByLocation(shortages);
        return ResponseEntity.ok(dto);
    }
}