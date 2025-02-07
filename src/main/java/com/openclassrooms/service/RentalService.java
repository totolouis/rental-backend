package com.openclassrooms.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals() {
        return (List<Rental>) rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Optional<Rental> updateRental(Long id, Rental updatedRental) {
        if (!id.equals(updatedRental.getId())) {
            return Optional.empty();
        }
    
        return rentalRepository.findById(id).map(existingRental -> {
            if (!existingRental.getOwnerId().equals(updatedRental.getOwnerId())) {
                return null;
            }
    
            if (updatedRental.getName() != null) existingRental.setName(updatedRental.getName());
            if (updatedRental.getSurface() != null) existingRental.setSurface(updatedRental.getSurface());
            if (updatedRental.getPrice() != null) existingRental.setPrice(updatedRental.getPrice());
            if (updatedRental.getPicture() != null) existingRental.setPicture(updatedRental.getPicture());
            if (updatedRental.getDescription() != null) existingRental.setDescription(updatedRental.getDescription());
    
            existingRental.setUpdatedAt(LocalDateTime.now());
            return rentalRepository.save(existingRental);
        });
    }
    
}