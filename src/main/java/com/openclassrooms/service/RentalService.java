package com.openclassrooms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.configuration.exceptions.GetFilePathException;
import com.openclassrooms.configuration.exceptions.SaveFileException;
import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
    // Needed to upload locally the images from the frontend
    private static final String UPLOAD_DIR = "..\\frontend\\src\\assets\\";
    private static final String FRONTEND_DIR = "assets\\";

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

    public Rental createRental(String name, Double surface, Double price, MultipartFile picture, String description,
            Integer ownerId) throws GetFilePathException, SaveFileException, IOException {
        String filePath = this.getFilepathFromMultipartFile(picture);
        if (filePath == null) {
            throw new GetFilePathException("Error getting filePath.");
        }
        Rental rental = new Rental(null, name, surface, price, filePath, description, ownerId, null, null);

        Rental newEntity = rentalRepository.save(rental);
        saveFile(Paths.get(filePath), picture.getBytes());

        return newEntity;
    }

    public Optional<Rental> updateRental(Long id, Rental updatedRental) {
        return rentalRepository.findById(id).map(existingRental -> {
            if (!existingRental.getOwnerId().equals(updatedRental.getOwnerId())) {
                return null;
            }

            if (updatedRental.getName() != null)
                existingRental.setName(updatedRental.getName());
            if (updatedRental.getSurface() != null)
                existingRental.setSurface(updatedRental.getSurface());
            if (updatedRental.getPrice() != null)
                existingRental.setPrice(updatedRental.getPrice());
            if (updatedRental.getPicture() != null)
                existingRental.setPicture(updatedRental.getPicture());
            if (updatedRental.getDescription() != null)
                existingRental.setDescription(updatedRental.getDescription());

            existingRental.setUpdatedAt(LocalDateTime.now());
            return rentalRepository.save(existingRental);
        });
    }

    private String getFilepathFromMultipartFile(MultipartFile file) throws GetFilePathException {
        try {
            if (file.getOriginalFilename() != null && file.getOriginalFilename().isEmpty()) {
                return null;
            }
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = FRONTEND_DIR + fileName;
            return filePath;
        } catch (IOException e) {
            throw new GetFilePathException("Error getting filePath.");
        }
    }

    private void saveFile(Path filePath, byte[] content) throws SaveFileException {
        try {
            Files.write(Paths.get(UPLOAD_DIR, filePath.getFileName().toString()), content);
        } catch (IOException e) {
            throw new SaveFileException("Error saving file.");
        }
    }

}