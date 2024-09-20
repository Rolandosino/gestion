package com.mediasoft.gestion_produit.repositories;

import com.mediasoft.gestion_produit.models.Produit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends CrudRepository<Produit, Long> {

    @Query("select p from Produit  p " +
            " left join fetch p.categorie")
    List<Produit> getProduits();
}
