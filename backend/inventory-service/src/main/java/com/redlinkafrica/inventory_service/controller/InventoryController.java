package com.redlinkafrica.inventory_service.controller;

import com.redlinkafrica.inventory_service.model.Inventory;
import com.redlinkafrica.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    // GET all inventory items
    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // GET inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET inventory by crossBorderId
    @GetMapping("/crossborder/{crossBorderId}")
    public ResponseEntity<Inventory> getInventoryByCrossBorderId(@PathVariable String crossBorderId) {
        Optional<Inventory> inventory = inventoryRepository.findByCrossBorderId(crossBorderId);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET inventory by bloodType
    @GetMapping("/bloodtype/{bloodType}")
    public List<Inventory> getInventoryByBloodType(@PathVariable String bloodType) {
        return inventoryRepository.findByBloodType(bloodType);
    }

    // GET inventory by hospitalId
    @GetMapping("/hospital/{hospitalId}")
    public List<Inventory> getInventoryByHospitalId(@PathVariable String hospitalId) {
        return inventoryRepository.findByHospitalId(hospitalId);
    }

    // GET inventory by bloodBankId
    @GetMapping("/bloodbank/{bloodBankId}")
    public List<Inventory> getInventoryByBloodBankId(@PathVariable String bloodBankId) {
        return inventoryRepository.findByBloodBankId(bloodBankId);
    }

    // GET inventory by town
    @GetMapping("/town/{town}")
    public List<Inventory> getInventoryByTown(@PathVariable String town) {
        return inventoryRepository.findByTown(town);
    }

    // GET inventory by country
    @GetMapping("/country/{country}")
    public List<Inventory> getInventoryByCountry(@PathVariable String country) {
        return inventoryRepository.findByCountry(country);
    }

    // GET inventory by donatedBy (query parameter)
    @GetMapping(params = "donatedBy")
    public List<Inventory> getInventoryByDonatedBy(@RequestParam Long donatedBy) {
        return inventoryRepository.findByDonatedBy(donatedBy);
    }

    // POST new inventory item
    @PostMapping("/add")
    public ResponseEntity<?> createInventory(@RequestBody Inventory inventory) {
        if (inventoryRepository.findByCrossBorderId(inventory.getCrossBorderId()).isPresent()) {
            return ResponseEntity.badRequest().body("crossBorderId already exists");
        }
        Inventory savedInventory = inventoryRepository.save(inventory);
        return ResponseEntity.ok(savedInventory);
    }

    // PUT update inventory
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if (inventory.isPresent()) {
            Inventory updatedInventory = inventory.get();
            updatedInventory.setBloodBankId(inventoryDetails.getBloodBankId());
            updatedInventory.setBloodType(inventoryDetails.getBloodType());
            updatedInventory.setVolume(inventoryDetails.getVolume());
            updatedInventory.setExpiryDate(inventoryDetails.getExpiryDate());
            updatedInventory.setDonatedBy(inventoryDetails.getDonatedBy());
            updatedInventory.setDonationDate(inventoryDetails.getDonationDate());
            updatedInventory.setCrossBorderId(inventoryDetails.getCrossBorderId());
            updatedInventory.setStatus(inventoryDetails.getStatus());
            updatedInventory.setTown(inventoryDetails.getTown());
            updatedInventory.setCountry(inventoryDetails.getCountry());
            updatedInventory.setHospitalId(inventoryDetails.getHospitalId());
            return ResponseEntity.ok(inventoryRepository.save(updatedInventory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE inventory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}