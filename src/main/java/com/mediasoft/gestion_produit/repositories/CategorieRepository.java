package com.mediasoft.gestion_produit.repositories;

import com.mediasoft.gestion_produit.models.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {

    @Query("select c from Categorie c")
    List<Categorie> getCategorie();
}
