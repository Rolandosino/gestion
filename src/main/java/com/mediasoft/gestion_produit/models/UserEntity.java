package com.mediasoft.gestion_produit.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mediasoft.gestion_produit.communs.entities.BaseEntity;
import lombok.*;

import java.util.Collection;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_entity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity extends BaseEntity implements UserDetails {

    /**
     * The ID.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username.
     */
    @Column(name = "username")
    private String username;

    /**
     * The user email address.
     */
    @Column(name = "emailAddress")
    private String emailAddress;

    /**
     * The user password.
     */
    @JsonIgnore
    @Column(name = "mot_de_passe")
    private String password;

    /**
     * The user roles.
     */
    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;


    /**
     * The user actif or not.
     */
    @Column(name = "actif")
    private boolean actif;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getLibelle().getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", actif=" + actif +
                '}';
    }
}
