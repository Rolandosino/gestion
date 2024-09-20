package com.mediasoft.gestion_produit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mediasoft.gestion_produit.enums.TypePermission.*;

@AllArgsConstructor
public enum TypeDeRole {
    UTILISATEUR(
            Set.of( UTILISATEUR_CREATE,
                    UTILISATEUR_READ,
                    UTILISATEUR_UPDATE,
                    UTILISATEUR_DELETE)
    ),
    MANAGER(
            Set.of(
                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE_UTILISATEUR,
                    MANAGER_DELETE_UTILISATEUR,

                    UTILISATEUR_CREATE,
                    UTILISATEUR_READ,
                    UTILISATEUR_UPDATE,
                    UTILISATEUR_DELETE
            )
    ),
    ADMINISTRATEUR(
            Set.of(
                    ADMINISTRATEUR_CREATE,
                    ADMINISTRATEUR_READ,
                    ADMINISTRATEUR_UPDATE,
                    ADMINISTRATEUR_DELETE,
                    ADMINISTRATEUR_CREATE_DONNEE_DE_BASE,
                    ADMINISTRATEUR_DELETE_DONNEE_DE_BASE,

                    MANAGER_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE_UTILISATEUR,
                    MANAGER_DELETE_UTILISATEUR
            )
    );

    @Getter
    Set<TypePermission> permissions;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> grantedAuthorities = this.getPermissions().stream().map(
                permission -> new SimpleGrantedAuthority(permission.name())
        ).collect(Collectors.toList());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
