package com.openclassrooms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.configuration.exceptions.GetFilePathException;
import com.openclassrooms.configuration.exceptions.SaveFileException;
import com.openclassrooms.configuration.exceptions.UserAlreadyExistsException;
import com.openclassrooms.dto.RentalDTO;
import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
    // Needed to upload locally the images from the frontend
    private static final String UPLOAD_DIR = "..\\frontend\\src\\assets\\";
    private static final String FRONTEND_DIR = "assets\\";

    @Autowired
    private ModelMapper modelMapper;

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = (List<Rental>) rentalRepository.findAll();
        List<RentalDTO> rentalDTOs = rentals.stream().map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
        return rentalDTOs;
    }

    public RentalDTO getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).get();
        RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
        return rentalDTO;
    }

    public RentalDTO createRental(String name, Double surface, Double price, MultipartFile picture, String description,
            Integer ownerId) throws GetFilePathException, SaveFileException, IOException {
        String filePath = this.getFilepathFromMultipartFile(picture);
        if (filePath == null) {
            throw new GetFilePathException("Error getting filePath.");
        }

        Rental rental = new Rental(null, name, surface, price, filePath, description, ownerId, null, null);

        Rental newEntity = rentalRepository.save(rental);
        saveFile(Paths.get(filePath), picture.getBytes());

        RentalDTO rentalDTO = modelMapper.map(newEntity, RentalDTO.class);
        return rentalDTO;
    }

    public RentalDTO updateRental(Long id, RentalDTO updatedRental) {
        Rental newDataRental = modelMapper.map(updatedRental, Rental.class);

        Rental rental = rentalRepository.findById(id).get();
        if (rental.getOwnerId().equals(newDataRental.getOwnerId())) {
            return saveRentalToRepository(rental, newDataRental);
        } else {
            // TODO: do a better exception
            throw new UserAlreadyExistsException("Owner id does not match.");
        }
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

    private RentalDTO saveRentalToRepository(Rental oldRental, Rental newDataRental) {
        if (newDataRental.getName() != null) {
            oldRental.setName(newDataRental.getName());
        }
        if (newDataRental.getSurface() != null) {
            oldRental.setSurface(newDataRental.getSurface());
        }
        if (newDataRental.getPrice() != null) {
            oldRental.setPrice(newDataRental.getPrice());
        }
        if (newDataRental.getPicture() != null) {
            oldRental.setPicture(newDataRental.getPicture());
        }
        if (newDataRental.getDescription() != null) {
            oldRental.setDescription(newDataRental.getDescription());
        }
        oldRental.setUpdatedAt(LocalDateTime.now());
        return modelMapper.map(rentalRepository.save(oldRental), RentalDTO.class);
    }

}