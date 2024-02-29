package com.alex.asset.security.config.jwt;

import com.alex.asset.email.EmailService;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.UserMapper;
import com.alex.asset.security.domain.dto.PasswordDto;
import com.alex.asset.security.domain.dto.RegisterDto;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final EmailService emailService;


    public boolean register(RegisterDto request) {
        log.info("Try to register user with email: {}", request.getEmail());
        if (userRepo.existsByEmail(request.getEmail())) return false;

        User user = UserMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        log.info("User with email {} was successfully created", request.getEmail());
        return true;
    }



    public boolean changePassword(PasswordDto dto, CustomPrincipal principal) {
        log.info("Try to change password for user with email: {}", principal.getName());
        User user = userRepo.getUserByEmail(principal.getName()).orElse(null);
        assert user != null;
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) return false;
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);
        return true;
    }


    public void changePasswordForgot(String email, String password) {
        log.info("Change password for user: {}", email);
        User user = userRepo.getUserByEmail(email).orElse(null);
        if (user == null) return;
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        emailService.passwordWasChanged(user.getEmail());
    }




    public boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

    public User getByEmail(String email) {
        return userRepo.getUserByEmail(email).orElse(null);
    }

    public User getById(Long userId) {
        return userRepo.findById(userId).orElse(null);
        }



}
