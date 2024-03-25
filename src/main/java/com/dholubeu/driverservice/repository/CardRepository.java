package com.dholubeu.driverservice.repository;

import com.dholubeu.driverservice.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}