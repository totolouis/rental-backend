package com.openclassrooms.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    Optional<Message> findById(Long id);
}
