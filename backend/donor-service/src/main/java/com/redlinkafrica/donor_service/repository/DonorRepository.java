package com.redlinkafrica.donor_service.repository;

import com.redlinkafrica.donor_service.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByEmail(String email);
    Optional<Donor> findByCrossBorderId(String crossBorderId);
    List<Donor> findByRole(Donor.Role role); // Find all donors or admins
}