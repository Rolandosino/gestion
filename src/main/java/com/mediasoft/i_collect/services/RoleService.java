package com.mediasoft.i_collect.services;

import com.mediasoft.i_collect.enums.TypeDeRole;
import com.mediasoft.i_collect.models.Role;
import com.mediasoft.i_collect.repositories.RoleRepository;
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
