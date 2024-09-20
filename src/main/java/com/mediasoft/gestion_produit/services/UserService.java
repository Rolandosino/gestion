package com.mediasoft.gestion_produit.services;

import com.mediasoft.gestion_produit.dto.UserInfosDto;
import com.mediasoft.gestion_produit.enums.TypeDeRole;
import com.mediasoft.gestion_produit.models.*;
import com.mediasoft.gestion_produit.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final ValidationService validationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final RoleService roleService;


    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getOne(Long id) {
        return entityManager.getReference(UserEntity.class, id);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserEntity getUserByEmail(String emailAddress) {
        return userRepository.findByEmail(emailAddress).orElse(null);
    }

    public UserEntity getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (UserEntity) authentication.getPrincipal();
        } catch (Exception e) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }

    @Modifying
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + email));
    }

    public UserEntity inscription(UserEntity user) throws MessagingException {

        if (!user.getEmailAddress().contains("@")) {
            throw new RuntimeException("Votre mail invalide");
        }
        if (!user.getEmailAddress().contains(".")) {
            throw new RuntimeException("Votre mail invalide");
        }

        Optional<UserEntity> utilisateurOptional = this.userRepository.findByEmail(user.getEmailAddress());
        if (utilisateurOptional.isPresent()) {
            throw new RuntimeException("Votre mail est déjà utilisé");
        }
        String mdpCrypte = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(mdpCrypte);

        Role roleUtilisateur = new Role();
        roleUtilisateur.setLibelle(TypeDeRole.UTILISATEUR);
        user.setRole(roleUtilisateur);
        user = this.userRepository.save(user);
        this.validationService.enregistrer(user);

        return user;
    }

    public UserEntity updateUser(UserInfosDto userInfosDto, Long idUser) throws MessagingException {
        UserEntity user = this.getOne(idUser);
        Role role = roleService.getRoleByType(userInfosDto.getRole());

        if (user != null) {
            user.setRole(role);
            user = userRepository.save(user);
        }
        return user;
    }

    public void activation(String code) {
        Validation validation = this.validationService.lireEnFonctionDuCode(code);
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }
        UserEntity utilisateurActiver = this.userRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.userRepository.save(utilisateurActiver);
    }
}
