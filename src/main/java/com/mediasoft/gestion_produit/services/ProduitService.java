package com.mediasoft.gestion_produit.services;

import com.mediasoft.gestion_produit.dto.ProduitDto;
import com.mediasoft.gestion_produit.models.Categorie;
import com.mediasoft.gestion_produit.models.Produit;
import com.mediasoft.gestion_produit.repositories.ProduitRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieService categorieService;
    private final EntityManager entityManager;

    public Produit getOne(Long id) {
        return entityManager.getReference(Produit.class, id);
    }

    public List<Produit> getProduits() {
        return produitRepository.getProduits();
    }

    public Produit saveProduit(ProduitDto produitDto) {
        Categorie categorie = categorieService.getOne(produitDto.getIdCategorie());
        Produit produit = Produit.builder()
                .libelleProduit(produitDto.getLibelle())
                .prix(produitDto.getPrixProduit() != null ? new BigDecimal(produitDto.getPrixProduit())
                        : BigDecimal.ZERO)
                .categorie(categorie)
                .build();
        return produitRepository.save(produit);
    }

    public Produit updateProduit(ProduitDto produitDto, Long idProduit) {
        Produit produit = this.getOne(idProduit);
        Categorie categorie = categorieService.getOne(produitDto.getIdCategorie());

        produit.setLibelleProduit(produitDto.getLibelle());
        produit.setPrix(produitDto.getPrixProduit() != null ? new BigDecimal(produitDto.getPrixProduit())
                : BigDecimal.ZERO);
        produit.setCategorie(categorie);
        return produitRepository.save(produit);
    }
}
