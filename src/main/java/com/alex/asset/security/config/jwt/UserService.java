package com.alex.asset.security.config.jwt;

import com.alex.asset.email.EmailService;
import com.alex.asset.security.domain.dto.PasswordDto;
import com.alex.asset.security.domain.dto.RegisterDto;
import com.alex.asset.utils.ErrorStatus;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserRepository userRepo;
    private final EmailService emailService;

    public boolean register(RegisterDto request) {
        log.info("Try to register user with email: {}", request.getEmail());
        try {
            User user = User.builder()
                    .firstname(request.getFirstName())
                    .lastname(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Role.CLIENT)
                    .isEnabled(false)
                    .build();
            userRepo.save(user);
            log.info("User with email {} was successfully created", request.getEmail());
            emailService.accountWasCreated(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            log.error(ErrorStatus.USER_ALREADY_EXISTS.name());
            log.error("User with email {} is already exists in DB", request.getEmail());
            return false;
        }
    }



    public boolean changePassword(PasswordDto dto, CustomPrincipal principal) {
        log.info("Try to change password for user with email: {}", principal.getEmail());
        User user = userRepo.findByEmail(principal.getEmail()).orElse(null);
        assert user != null;
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) return false;
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(user);
        return true;
    }


    public boolean changePassword(String email, String password) {
        log.info("Change password for user: {}", email);
        User user = userRepo.findByEmail(email).orElse(null);
        assert user != null;
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
        emailService.passwordWasChanged(user.getEmail());

        return true;
    }




    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
