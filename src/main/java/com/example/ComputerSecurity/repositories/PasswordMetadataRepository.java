package com.example.ComputerSecurity.repositories;

import com.example.ComputerSecurity.entities.PasswordMetadata;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordMetadataRepository extends CrudRepository<PasswordMetadata, String> {

    @Override
    Optional<PasswordMetadata> findById(String userId);

    @Override
    PasswordMetadata save(PasswordMetadata s);
}
