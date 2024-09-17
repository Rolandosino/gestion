package com.mediasoft.i_collect.repositories;

import com.mediasoft.i_collect.models.Validation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidationRepository extends CrudRepository<Validation, Long> {
    Optional<Validation> findByCode(String code);
}
