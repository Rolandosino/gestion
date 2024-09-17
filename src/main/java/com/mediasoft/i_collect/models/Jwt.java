package com.mediasoft.i_collect.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "jwt")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jwt implements Serializable{
    /**
     * The ID.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valeur")
    private String valeur;

    @Column(name = "desactive")
    private boolean desactive;

    @Column(name = "expire")
    private boolean expire;

    @JoinColumn(name = "refresh_token_id")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private RefreshToken refreshToken;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "id_user")
    private UserEntity user;

}
