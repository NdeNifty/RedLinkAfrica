package com.redlinkafrica.donor_service.controller;

import com.redlinkafrica.donor_service.model.DonationDTO;
import com.redlinkafrica.donor_service.model.Donor;
import com.redlinkafrica.donor_service.model.RequestDTO;
import com.redlinkafrica.donor_service.model.UserDTO;
import com.redlinkafrica.donor_service.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private RestTemplate restTemplate; // For inter-service communication

    // GET all donors
    @GetMapping
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    // GET donor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        Optional<Donor> donor = donorRepository.findById(id);
        return donor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET donor by email
    @GetMapping("/email/{email}")
    public ResponseEntity<Donor> getDonorByEmail(@PathVariable String email) {
        Optional<Donor> donor = donorRepository.findByEmail(email);
        return donor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET donor by crossBorderId
    @GetMapping("/crossborder/{crossBorderId}")
    public ResponseEntity<Donor> getDonorByCrossBorderId(@PathVariable String crossBorderId) {
        Optional<Donor> donor = donorRepository.findByCrossBorderId(crossBorderId);
        return donor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET donors by role
    @GetMapping("/role/{role}")
    public List<Donor> getDonorsByRole(@PathVariable Donor.Role role) {
        return donorRepository.findByRole(role);
    }

    @PostMapping
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
        if (donor.getUserId() != null) {
            String authUrl = "http://localhost:8085/api/auth/users/" + donor.getUserId();
            ResponseEntity<UserDTO> userResponse = restTemplate.getForEntity(authUrl, UserDTO.class);
            if (!userResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.badRequest().body(null);
            }
        }
        return ResponseEntity.ok(donorRepository.save(donor));
    }
    // PUT update donor
    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long id, @RequestBody Donor donorDetails) {
        Optional<Donor> donor = donorRepository.findById(id);
        if (donor.isPresent()) {
            Donor updatedDonor = donor.get();
            updatedDonor.setName(donorDetails.getName());
            updatedDonor.setEmail(donorDetails.getEmail());
            updatedDonor.setPhone(donorDetails.getPhone());
            updatedDonor.setTown(donorDetails.getTown());
            updatedDonor.setCountry(donorDetails.getCountry());
            updatedDonor.setBloodType(donorDetails.getBloodType());
            updatedDonor.setEligible(donorDetails.isEligible());
            updatedDonor.setCrossBorderId(donorDetails.getCrossBorderId());
            updatedDonor.setRole(donorDetails.getRole());
            return ResponseEntity.ok(donorRepository.save(updatedDonor));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE donor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        if (donorRepository.existsById(id)) {
            donorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/donors/{id}/donations - Get donor's donation history
    @GetMapping("/{id}/donations")
    public ResponseEntity<DonationDTO[]> getDonorDonations(@PathVariable Long id) {
        Optional<Donor> donor = donorRepository.findById(id);
        if (!donor.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        String inventoryUrl = "http://localhost:8081/api/inventory?donatedBy=" + id;
        ResponseEntity<Object[]> inventoryResponse = restTemplate.getForEntity(inventoryUrl, Object[].class);
        if (inventoryResponse.getStatusCode().is2xxSuccessful()) {
            Object[] responseData = inventoryResponse.getBody();
            DonationDTO[] donations = new DonationDTO[responseData.length];
            for (int i = 0; i < responseData.length; i++) {
                donations[i] = mapToDonationDTO((Map<String, Object>) responseData[i]);
            }
            return ResponseEntity.ok(donations);
        }
        return ResponseEntity.status(inventoryResponse.getStatusCode()).build();
    }

    // GET /api/donors/{id}/requests - Get donor's request history
    @GetMapping("/{id}/requests")
    public ResponseEntity<RequestDTO[]> getDonorRequests(@PathVariable Long id) {
        Optional<Donor> donor = donorRepository.findById(id);
        if (!donor.isPresent()) {

            return ResponseEntity.notFound().build();
        }

        String requestUrl = "http://localhost:8087/api/requests?donorId=" + id;
        ResponseEntity<Object[]> requestResponse = restTemplate.getForEntity(requestUrl, Object[].class);
        if (requestResponse.getStatusCode().is2xxSuccessful()) {
            Object[] responseData = requestResponse.getBody();
            RequestDTO[] requests = new RequestDTO[responseData.length];
            for (int i = 0; i < responseData.length; i++) {
                requests[i] = mapToRequestDTO((Map<String, Object>) responseData[i]);
            }
            return ResponseEntity.ok(requests);
        }
        return ResponseEntity.status(requestResponse.getStatusCode()).build();
    }

    // Helper method to map JSON response to DonationDTO
    private DonationDTO mapToDonationDTO(Map<String, Object> data) {
        DonationDTO dto = new DonationDTO();
        dto.setId(data.get("id") != null ? ((Number) data.get("id")).longValue() : null);
        dto.setBloodBankId((String) data.get("bloodBankId"));
        dto.setBloodType((String) data.get("bloodType"));
        dto.setVolume(data.get("volume") != null ? ((Number) data.get("volume")).intValue() : 0);
        dto.setExpiryDate(data.get("expiryDate") != null ? LocalDate.parse(data.get("expiryDate").toString()) : null);
        dto.setDonatedBy(data.get("donatedBy") != null ? ((Number) data.get("donatedBy")).longValue() : null);
        dto.setDonationDate(data.get("donationDate") != null ? LocalDate.parse(data.get("donationDate").toString()) : null);
        dto.setTown((String) data.get("town"));
        dto.setCountry((String) data.get("country"));
        dto.setHospitalId((String) data.get("hospitalId"));
        dto.setCrossBorderId((String) data.get("crossBorderId"));
        dto.setStatus((String) data.get("status"));
        return dto;
    }

    // Helper method to map JSON response to RequestDTO
    private RequestDTO mapToRequestDTO(Map<String, Object> data) {
        RequestDTO dto = new RequestDTO();
        dto.setId(data.get("id") != null ? ((Number) data.get("id")).longValue() : null);
        dto.setHospitalId((String) data.get("hospitalId"));
        dto.setBloodType((String) data.get("bloodType"));
        dto.setQuantity(data.get("quantity") != null ? ((Number) data.get("quantity")).intValue() : 0);
        dto.setUrgency((String) data.get("urgency"));
        dto.setRequestDate(data.get("requestDate") != null ? LocalDateTime.parse(data.get("requestDate").toString()) : null);
        dto.setCrossBorderId((String) data.get("crossBorderId"));
        dto.setStatus((String) data.get("status"));
        dto.setDonorId(data.get("donorId") != null ? ((Number) data.get("donorId")).longValue() : null);
        return dto;
    }
}