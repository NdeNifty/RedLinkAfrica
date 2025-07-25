package com.redlinkafrica.request_service.repository;

import com.redlinkafrica.request_service.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByBloodTypeAndUrgency(String bloodType, Request.Urgency urgency);
    List<Request> findByHospitalId(String hospitalId);
    List<Request> findByCrossBorderId(String crossBorderId);
    List<Request> findByStatus(String status);
}