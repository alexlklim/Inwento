package com.alex.asset.email;


import com.alex.asset.security.domain.User;
import com.alex.asset.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class EmailService {


    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void accountWasCreated(User user) {
        log.info("account was created for user: {}", user.getEmail());
        MailStructure mail = new MailStructure();
        mail.setEmail(user.getEmail());
        mail.setSubject("Asset Track Pro");
        mail.setMessage("For " + user.getFirstname() + " " + user.getLastname() +
                " account was created in Inventory System. Please login using this link  \n" +
                Utils.ENDPOINT_LOGIN);
        sendMail(mail);

    }

    public void forgotPassword(String token, String email) {
        log.info("Someone try to get access to your account: " + token);

        MailStructure mail = new MailStructure();
        mail.setEmail(email);
        mail.setSubject("Asset Track Pro");
        mail.setMessage("Link to restore password  \n" +
                Utils.ENDPOINT_RECOVERY + token);
        sendMail(mail);
    }

    public void passwordWasChanged(String email) {
        log.info("Password was changed for user: " + email);
        MailStructure mail = new MailStructure();
        mail.setEmail(email);
        mail.setSubject("Asset Track Pro");
        mail.setMessage("Password was changed. Give us to know if you didn't do it");
        sendMail(mail);
    }


    private void sendMail(MailStructure mailStructure) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(mailStructure.getEmail());
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        mailSender.send(simpleMailMessage);

    }
}
