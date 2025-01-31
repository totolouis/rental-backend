package com.openclassrooms.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
    Optional<Rental> findById(Long id);
}
