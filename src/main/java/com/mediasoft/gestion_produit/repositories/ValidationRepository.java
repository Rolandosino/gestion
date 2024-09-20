package com.mediasoft.gestion_produit.repositories;

import com.mediasoft.gestion_produit.models.Validation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends CrudRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);
}
