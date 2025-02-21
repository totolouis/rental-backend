package com.openclassrooms.controller;

import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "Get all rentals")
    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @Operation(summary = "Get a rental by id")
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @Operation(summary = "Create a rental")
    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@RequestParam String name,
            @RequestParam Double surface,
            @RequestParam Double price, @RequestParam MultipartFile picture, @RequestParam String description) {
        Integer ownerId = getOwnerId();

        Rental savedRental = rentalService.createRental(name, surface, price, picture, description, ownerId);

        // TODO: use a mapper instead
        return ResponseEntity.ok(new RentalDTO(name, surface, price, savedRental.getPicture(), description));
    }

    @Operation(summary = "Update a rental")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestParam String name,
            @RequestParam Double surface,
            @RequestParam Double price,
            @RequestParam String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Integer ownerId = Math.toIntExact(jwt.getClaim("id"));
        Rental rental = new Rental(id, name, surface, price, description, ownerId);
        Optional<Rental> updatedRental = rentalService.updateRental(id, rental);
        if (updatedRental.isPresent()) {

            return ResponseEntity.ok(new RentalDTO(name, surface, price, name, description));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Integer getOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Math.toIntExact(jwt.getClaim("id"));
    }
}
