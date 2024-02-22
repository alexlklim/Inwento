package com.alex.asset.email;


import com.alex.asset.security.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class EmailService {
    public void accountWasCreated(User user){
        log.info("account was created for user: {}", user.getEmail());
    }

    public void forgotPassword(String token){
        log.info("Someone try to get access to your account: " + token);
    }

    public void passwordWasChanged(String email) {
        log.info("Password was changed for user: " + email);
    }
}
