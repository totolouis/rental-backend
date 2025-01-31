package com.openclassrooms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> findById(Long id);
}
