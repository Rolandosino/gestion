package com.mediasoft.i_collect.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh-token")
public class RefreshToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "expire")
    private boolean expire;

    @Column(name = "valeur")
    private String valeur;
    
    @Column(name = "creation")
    private Instant creation;

    @Column(name = "expiration")
    private Instant expiration;

}
