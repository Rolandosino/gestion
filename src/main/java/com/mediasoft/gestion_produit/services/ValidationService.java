package com.mediasoft.gestion_produit.services;

import com.mediasoft.gestion_produit.models.UserEntity;
import com.mediasoft.gestion_produit.models.Validation;
import com.mediasoft.gestion_produit.repositories.ValidationRepository;
import com.mediasoft.gestion_produit.security.utils.MailSenderUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
public class ValidationService {
    private final ValidationRepository validationRepository;
    private final MailSenderUtils mailSender;

    public void enregistrer(UserEntity utilisateur) throws MessagingException {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        validation = this.validationRepository.save(validation);
        this.mailSender.sendActivationAccountMail(validation);
    }

    public void updateValidation(Validation validation) {
        this.validationRepository.save(validation);
    }

    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }
}
