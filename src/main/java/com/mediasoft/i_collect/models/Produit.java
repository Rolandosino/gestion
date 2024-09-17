package com.mediasoft.i_collect.models;

import com.mediasoft.i_collect.communs.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produit")
public class Produit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "libelle_produit")
    private String libelleProduit;

    @Column(name = "prix_produit")
    private BigDecimal prix;

    @JoinColumn(name = "id_categorie")
    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return idProduit.equals(produit.idProduit);
    }

    @Override
    public int hashCode() {
        int has = 13;
        has += 47 + Objects.hash(idProduit);
        return has;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", libelleProduit='" + libelleProduit + '\'' +
                ", prix=" + prix +
                '}';
    }
}
