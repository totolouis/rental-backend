package com.openclassrooms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
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

import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private static final String UPLOAD_DIR = "src\\frontend\\src\\assets\\";
    private static final String FRONTEND_DIR = "assets\\";

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        return ResponseEntity.ok(Map.of("rentals", rentalService.getAllRentals()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRental(@RequestParam String name, @RequestParam Double surface,
            @RequestParam Double price, @RequestParam MultipartFile picture, @RequestParam String description) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Integer ownerId = Math.toIntExact(jwt.getClaim("id"));
        // I dont like the fact to give a null just to let the db use it. The
        // constructor should be clearer, like no need to put the

        try {
            String filePath = this.getFilepathFromMultipartFile(picture);
            if (filePath == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Picture is missing");
            }

            // Save rental with file path in DB
            Rental rental = Rental.builder()
                    .name(name)
                    .surface(surface)
                    .price(price)
                    .description(description)
                    .ownerId(ownerId)
                    .picture(filePath) // Save file path
                    .build();

            rentalService.createRental(rental);
            saveFile(Paths.get(filePath), picture.getBytes());
            return ResponseEntity.ok(rental);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(@PathVariable Long id, @RequestParam String name,
            @RequestParam Double surface,
            @RequestParam Double price,
            @RequestParam String description) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Integer ownerId = Math.toIntExact(jwt.getClaim("id"));
        Rental rental = Rental.builder()
                .name(name)
                .surface(surface)
                .price(price)
                .description(description)
                .ownerId(ownerId)
                .build();
        Optional<Rental> updatedRental = rentalService.updateRental(id, rental);
        if (updatedRental.isPresent()) {

            return ResponseEntity.ok(updatedRental);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String getFilepathFromMultipartFile(MultipartFile file) throws IOException {
        try {
            if (file.getOriginalFilename().isEmpty()) {
                return null;
            }
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = FRONTEND_DIR + fileName;
            return filePath;
        } catch (IOException e) {
            throw new IOException("Error getting filePath.");
        }
    }

    private void saveFile(Path filePath, byte[] content) throws IOException {
        try {
            Files.write(Paths.get(UPLOAD_DIR, filePath.getFileName().toString()), content);
        } catch (IOException e) {
            throw new IOException("Error saving file.");
        }
    }

}
