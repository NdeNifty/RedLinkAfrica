package com.redlinkafrica.campaign_service.repository;

import com.redlinkafrica.campaign_service.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByDateAfter(LocalDateTime date);
    List<Campaign> findByLocationContaining(String location);
    List<Campaign> findByStatus(String status);
    List<Campaign> findByCountry(String status);
    List<Campaign> findByTown(String status);
    List<Campaign> findByCrossBorderId(String crossBorderId);
}