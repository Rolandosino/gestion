package com.mediasoft.gestion_produit.controllers;

import com.mediasoft.gestion_produit.communs.utils.BaseController;
import com.mediasoft.gestion_produit.dto.ProduitDto;
import com.mediasoft.gestion_produit.models.Produit;
import com.mediasoft.gestion_produit.services.ProduitService;
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
@RequestMapping(ProduitController.API_ROOT)
@RestController
@Tag(name = "Produit controller")
public class ProduitController extends BaseController {

    /**
     * Users API root.
     */
    public static final String API_ROOT = "/produits";
    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<Produit>> getProduits() {
        try {
            List<Produit> produits = produitService.getProduits();
            return new ResponseEntity<>(produits, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveProduit(@RequestBody ProduitDto produitDto) {
        try {
            produitService.saveProduit(produitDto);
            return new ResponseEntity<>("Produit enregistré avec succès", HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateProduit(@RequestParam(value = "idProduit") Long idProduit,
                                                @RequestBody ProduitDto produitDto) {
        try {
            produitService.updateProduit(produitDto, idProduit);
            return new ResponseEntity<>("Produit modifié avec succès", HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
