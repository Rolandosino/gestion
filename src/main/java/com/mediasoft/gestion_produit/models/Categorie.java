package com.mediasoft.gestion_produit.models;

import com.mediasoft.gestion_produit.communs.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categorie")
public class Categorie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private Long idCategorie;

    @Column(name = "libelle")
    private String libelle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return idCategorie.equals(categorie.idCategorie);
    }

    @Override
    public int hashCode() {
        int has = 13;
        has += 47 + Objects.hash(idCategorie);
        return has;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idCategorie=" + idCategorie +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
