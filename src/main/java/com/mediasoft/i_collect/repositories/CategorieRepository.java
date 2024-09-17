package com.mediasoft.i_collect.repositories;

import com.mediasoft.i_collect.models.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {

    @Query("select c from Categorie c")
    List<Categorie> getCategorie();
}
