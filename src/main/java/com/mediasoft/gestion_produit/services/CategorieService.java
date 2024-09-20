package com.mediasoft.gestion_produit.services;

import com.mediasoft.gestion_produit.models.Categorie;
import com.mediasoft.gestion_produit.repositories.CategorieRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final EntityManager entityManager;

    public Categorie getOne(Long id) {
        return entityManager.getReference(Categorie.class, id);
    }
}
