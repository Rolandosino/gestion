package com.mediasoft.gestion_produit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProduitDto {

    @JsonProperty("libelle")
    private String libelle;
    @JsonProperty("prixProduit")
    private String prixProduit;
    @JsonProperty("idCategorie")
    private Long idCategorie;
}
