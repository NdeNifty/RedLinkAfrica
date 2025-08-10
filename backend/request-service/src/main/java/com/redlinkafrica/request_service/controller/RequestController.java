package com.redlinkafrica.request_service.controller;

import com.redlinkafrica.request_service.model.Request;
import com.redlinkafrica.request_service.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    // GET all requests
    @GetMapping
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    // GET request by ID
    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        Optional<Request> request = requestRepository.findById(id);
        return request.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET requests by hospitalId
    @GetMapping("/hospital/{hospitalId}")
    public List<Request> getRequestsByHospitalId(@PathVariable String hospitalId) {
        return requestRepository.findByHospitalId(hospitalId);
    }

    // GET requests by donorId
    @GetMapping("/donor/{donorId}")
    public List<Request> getRequestsByDonorId(@PathVariable Long donorId) {
        return requestRepository.findByDonorId(donorId);
    }

    // GET requests by bloodType and urgency
    @GetMapping("/bloodtype/{bloodType}/urgency/{urgency}")
    public List<Request> getRequestsByBloodTypeAndUrgency(
            @PathVariable String bloodType,
            @PathVariable Request.Urgency urgency) {
        return requestRepository.findByBloodTypeAndUrgency(bloodType, urgency);
    }

    // POST new request
    @PostMapping
    public Request createRequest(@RequestBody Request request) {
        return requestRepository.save(request);
    }

    // PUT update request
    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request requestDetails) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isPresent()) {
            Request updatedRequest = request.get();
            updatedRequest.setHospitalId(requestDetails.getHospitalId());
            updatedRequest.setBloodType(requestDetails.getBloodType());
            updatedRequest.setQuantity(requestDetails.getQuantity());
            updatedRequest.setUrgency(requestDetails.getUrgency());
            updatedRequest.setRequestDate(requestDetails.getRequestDate());
            updatedRequest.setCrossBorderId(requestDetails.getCrossBorderId());
            updatedRequest.setStatus(requestDetails.getStatus());
            updatedRequest.setDonorId(requestDetails.getDonorId());
            return ResponseEntity.ok(requestRepository.save(updatedRequest));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE request
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}