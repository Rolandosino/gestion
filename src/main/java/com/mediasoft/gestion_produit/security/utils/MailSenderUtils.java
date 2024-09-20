package com.mediasoft.gestion_produit.security.utils;

import com.mediasoft.gestion_produit.models.Validation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailSenderUtils {
    private static final String FROM_EMAIL = "anicet.agbonon@gmail.com";
    private static final String SUBJECT = "Code d'activation compte iCollect";
    private static final String HTML_TEMPLATE = "<h3>Bonjour %s,</h3>"
            + "<p>Votre code d'activation est <strong>%s</strong>.</p>"
            + "<p>Merci et à bientôt!</p>";

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendActivationAccountMail(Validation validation) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress(FROM_EMAIL));
        helper.setTo(validation.getUtilisateur().getEmailAddress());
        helper.setSubject(SUBJECT);

        String htmlContent = String.format(HTML_TEMPLATE,
                validation.getUtilisateur().getUsername(),
                validation.getCode());

        helper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
