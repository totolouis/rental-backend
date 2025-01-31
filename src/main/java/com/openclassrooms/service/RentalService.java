package com.openclassrooms.service;

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
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    // TODO: may be not used flush. Read more about it.
    public Rental createRental(Rental rental) {
        return rentalRepository.saveAndFlush(rental);
    }

    public Optional<Rental> updateRental(Long id, Rental updatedRental) {
        return rentalRepository.findById(id).map(existingRental -> {
            existingRental.setName(updatedRental.getName());
            existingRental.setSurface(updatedRental.getSurface());
            existingRental.setPrice(updatedRental.getPrice());
            existingRental.setPicture(updatedRental.getPicture());
            existingRental.setDescription(updatedRental.getDescription());
            return rentalRepository.saveAndFlush(existingRental);
        });
    }
}