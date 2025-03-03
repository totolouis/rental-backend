package com.openclassrooms.controller;

import com.openclassrooms.configuration.exceptions.GetFilePathException;
import com.openclassrooms.configuration.exceptions.SaveFileException;
import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import java.io.IOException;
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
@SecurityRequirement(name = "bearerAuth")
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

        // TODO": not uniform. giving dto to update and fields to create?
        // CHANGE IT. OMG CA MENERVE QUE LE GLOBAL NE MARHCE PAS
        RentalDTO savedRental = null;
        try {
            savedRental = rentalService.createRental(name, surface, price, picture, description, ownerId);
        } catch (GetFilePathException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SaveFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO: use a mapper instead
        return ResponseEntity.ok(savedRental);
    }

    @Operation(summary = "Update a rental")
    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @RequestParam String name,
            @RequestParam Double surface,
            @RequestParam Double price,
            @RequestParam String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Integer ownerId = Math.toIntExact(jwt.getClaim("id"));
        RentalDTO rentalDTO = new RentalDTO(id, name, surface, price, description, ownerId);

        return ResponseEntity.ok(rentalService.updateRental(id, rentalDTO));
    }

    private Integer getOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Math.toIntExact(jwt.getClaim("id"));
    }
}
