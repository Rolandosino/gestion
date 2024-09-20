package com.mediasoft.gestion_produit.controllers;

import com.mediasoft.gestion_produit.communs.utils.BaseController;
import com.mediasoft.gestion_produit.models.Categorie;
import com.mediasoft.gestion_produit.repositories.CategorieRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@AllArgsConstructor
@RequestMapping(CategorieController.API_ROOT)
@RestController
@Tag(name = "Categorie controller")
public class CategorieController extends BaseController {

    /**
     * Users API root.
     */
    public static final String API_ROOT = "/categories";
    private final CategorieRepository categorieRepository;

    @GetMapping
    public ResponseEntity<List<Categorie>> getCategories() {
        try {
            List<Categorie> categories = categorieRepository.getCategorie();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Categorie> saveCategorie(@RequestParam(value = "libelle") String libelle) {
        try {
            Categorie categorie = Categorie.builder()
                    .libelle(libelle)
                    .build();
            categorie = categorieRepository.save(categorie);
            return new ResponseEntity<>(categorie, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
