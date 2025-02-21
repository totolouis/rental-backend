package com.openclassrooms.dto;

import java.time.LocalDateTime;

public class RentalDTO {
    private Long id;
    private String name;
    private Double surface;
    private Double price;
    private String picture;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer ownerId;

    public RentalDTO(Long id, String name, Double surface, Double price, String picture, String description,
            LocalDateTime createdAt, LocalDateTime updatedAt, Integer ownerId) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerId = ownerId;
    }

    public RentalDTO(String name, Double surface, Double price, String picture, String description, Integer ownerId) {
        this(null, name, surface, price, picture, description, null, null, ownerId);
    }

    public RentalDTO(String name, Double surface, Double price, String picture, String description) {
        this(null, name, surface, price, picture, description, null, null, null);
    }

    public RentalDTO(Long id, String name, Double surface, Double price, String description, Integer ownerId) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.description = description;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getSurface() {
        return surface;
    }

    public Double getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Integer getOwnerId() {
        return ownerId;
    }
}
