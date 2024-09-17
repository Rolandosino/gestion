package com.mediasoft.i_collect.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mediasoft.i_collect.enums.TypeDeRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "libelle")
    @Enumerated(EnumType.STRING)
    private TypeDeRole libelle;
}
