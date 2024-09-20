package com.mediasoft.gestion_produit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "valisation")
public class Validation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation")
    private Instant creation;

    @Column(name = "expiration")
    private Instant expiration;

    @Column(name = "activation")
    private Instant activation;

    @Column(name = "code")
    private String code;

    @JoinColumn(name = "utilisateur_id")
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity utilisateur;

}
