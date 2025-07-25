package com.redlinkafrica.inventory_service.repository;

import com.redlinkafrica.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByBloodType(String bloodType);
    Optional<Inventory> findByCrossBorderId(String crossBorderId);
    List<Inventory> findByStatus(String status);
    List<Inventory> findByBloodBankIdAndBloodType(String bloodBankId, String bloodType);
}