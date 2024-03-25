package com.dholubeu.driverservice.repository;

import com.dholubeu.driverservice.domain.Driver;
import com.dholubeu.driverservice.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    boolean existsByEmail(String email);

    Optional<Driver> findByEmail(String email);

    List<Driver> findAllByStatus(Status status);

}