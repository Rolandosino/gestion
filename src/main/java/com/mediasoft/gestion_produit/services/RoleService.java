package com.mediasoft.gestion_produit.services;

import com.mediasoft.gestion_produit.enums.TypeDeRole;
import com.mediasoft.gestion_produit.models.Role;
import com.mediasoft.gestion_produit.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    public Role getRoleByType(String libelle) {
        if (libelle != null) {
            switch (libelle) {
                case "utilisateur":
                    return roleRepository.getRoleByTypeRole(TypeDeRole.UTILISATEUR);
                case "manager":
                    return roleRepository.getRoleByTypeRole(TypeDeRole.MANAGER);
                case "admin":
                    return roleRepository.getRoleByTypeRole(TypeDeRole.ADMINISTRATEUR);
                default:
                    return roleRepository.getRoleByTypeRole(TypeDeRole.UTILISATEUR);
            }
        }
        return null;
    }
}
