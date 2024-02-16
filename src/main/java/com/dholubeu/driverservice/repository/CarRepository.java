package com.dholubeu.driverservice.repository;

import com.dholubeu.driverservice.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByNumber(String number);

}