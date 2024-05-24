package com.alex.asset.email;


import com.alex.asset.company.domain.Company;
import com.alex.asset.company.service.CompanyRepo;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.Utils;
import com.alex.asset.exceptions.email.EmailIsNotConfigured;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@Configuration
@RequiredArgsConstructor
public class EmailService {
     JavaMailSender mailSender;
     CompanyRepo companyRepo;


    @Autowired
    public EmailService(JavaMailSender mailSender, CompanyRepo companyRepo) {
        this.mailSender = mailSender;
        this.companyRepo = companyRepo;
    }


    public void applyNewConfiguration() {
        Company company = companyRepo.findAll().get(0);
        System.out.println(company.toString());
    }


    public void accountWasCreated(User user) {
        applyNewConfiguration();
        log.info("Send email about creating account to user with email: {}", user.getEmail());
        MailStructure mail = new MailStructure();
        mail.setEmail(user.getEmail());
        mail.setSubject("Asset Track Pro");
        mail.setMessage("For " + user.getFirstname() + " " + user.getLastname() +
                " account was created in Inventory System. Please login using this link  \n" +
                Utils.ENDPOINT_LOGIN);
        sendMail(mail);

    }

    public void forgotPassword(String token, String email) {
        log.info("Send email forgot password to user with email: {}", email);

        MailStructure mail = new MailStructure();
        mail.setEmail(email);
        mail.setSubject("Asset Track Pro");
        mail.setMessage("Link to restore password  \n" +
                Utils.ENDPOINT_RECOVERY + token);
        sendMail(mail);
    }

    public void passwordWasChanged(String email) {
        log.info("Send email what password was changed to user with email: {}", email);
        MailStructure mail = new MailStructure();
        mail.setEmail(email);
        mail.setSubject("Asset Track Pro");
        mail.setMessage("Password was changed. Give us to know if you didn't do it");
        sendMail(mail);
    }


    @SneakyThrows
     void sendMail(MailStructure mailStructure) {
        Company company = companyRepo.findAll().get(0);
        if (!company.getIsEmailConfigured()) throw new EmailIsNotConfigured("Email is not configured");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(company.getUsername());
        simpleMailMessage.setTo(mailStructure.getEmail());
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        mailSender.send(simpleMailMessage);

    }
}