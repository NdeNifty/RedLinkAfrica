package com.redlinkafrica.campaign_service.controller;

import com.redlinkafrica.campaign_service.model.Campaign;
import com.redlinkafrica.campaign_service.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    // GET all campaigns
    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    // GET campaign by ID
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        Optional<Campaign> campaign = campaignRepository.findById(id);
        return campaign.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET campaigns by town
    @GetMapping("/town/{town}")
    public List<Campaign> getCampaignsByTown(@PathVariable String town) {
        return campaignRepository.findByTown(town);
    }

    // GET campaigns by country
    @GetMapping("/country/{country}")
    public List<Campaign> getCampaignsByCountry(@PathVariable String country) {
        return campaignRepository.findByCountry(country);
    }

    // GET campaigns by status
    @GetMapping("/status/{status}")
    public List<Campaign> getCampaignsByStatus(@PathVariable String status) {
        return campaignRepository.findByStatus(status);
    }

    // POST new campaign
    @PostMapping
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    // PUT update campaign
    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @RequestBody Campaign campaignDetails) {
        Optional<Campaign> campaign = campaignRepository.findById(id);
        if (campaign.isPresent()) {
            Campaign updatedCampaign = campaign.get();
            updatedCampaign.setName(campaignDetails.getName());
            updatedCampaign.setStartDate(campaignDetails.getStartDate());
            updatedCampaign.setEndDate(campaignDetails.getEndDate());
            updatedCampaign.setTown(campaignDetails.getTown());
            updatedCampaign.setCountry(campaignDetails.getCountry());
            updatedCampaign.setTargetDonors(campaignDetails.getTargetDonors());
            updatedCampaign.setStatus(campaignDetails.getStatus());
            updatedCampaign.setDonorIds(campaignDetails.getDonorIds());
            return ResponseEntity.ok(campaignRepository.save(updatedCampaign));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE campaign
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        if (campaignRepository.existsById(id)) {
            campaignRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}