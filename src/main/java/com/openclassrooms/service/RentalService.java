package com.openclassrooms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.openclassrooms.mapper.RentalMapper;
import com.openclassrooms.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
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

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<RentalDTO> getAllRentals() {
        RentalMapper mapper = Mappers.getMapper(RentalMapper.class);
        List<Rental> rentals = (List<Rental>) rentalRepository.findAll();

        return rentals.stream().map(mapper::fromRental).toList();
    }

    public RentalDTO getRentalById(Long id) {
        RentalMapper mapper = Mappers.getMapper(RentalMapper.class);
        Rental rental = rentalRepository.findById(id).get();
        return mapper.fromRental(rental);
    }

    public RentalDTO createRental(String name, Double surface, Double price, MultipartFile picture, String description,
                                  Integer ownerId) throws GetFilePathException, SaveFileException, IOException {
        RentalMapper mapper = Mappers.getMapper(RentalMapper.class);
        String filePath = getFilePath(picture);

        RentalDTO rentalDTO = new RentalDTO(name, surface, price, filePath, description, ownerId);

        Rental newEntity = rentalRepository.save(mapper.toRental(rentalDTO));
        saveFile(Paths.get(filePath), picture.getBytes());

        return mapper.fromRental(newEntity);
    }

    public RentalDTO updateRental(Long id, RentalDTO updatedRental) {
        RentalMapper mapper = Mappers.getMapper(RentalMapper.class);
        Rental newDataRental = mapper.toRental(updatedRental);
        Rental rental = rentalRepository.findById(id).get();
        if (rental.getOwnerId().equals(newDataRental.getOwnerId())) {
            return saveRentalToRepository(rental, newDataRental);
        } else {
            throw new UserAlreadyExistsException("Owner id does not match.");
        }
    }

    private String getFilePath(MultipartFile picture) throws GetFilePathException {
        String filePath = this.getFilepathFromMultipartFile(picture);
        if (filePath == null) {
            throw new GetFilePathException("Error getting filePath.");
        }
        return filePath;
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
        RentalMapper mapper = Mappers.getMapper(RentalMapper.class);
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
        return mapper.fromRental(rentalRepository.save(oldRental));
    }

}