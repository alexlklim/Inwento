package com.alex.asset.security.config.jwt;

import com.alex.asset.email.EmailService;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.domain.UserMapper;
import com.alex.asset.security.domain.dto.PasswordDto;
import com.alex.asset.security.domain.dto.RegisterDto;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {
    private final String TAG = "USER AUTHENTICATION SERVICE - ";

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final EmailService emailService;


    public boolean register(RegisterDto request) {
        log.info(TAG + "Register user with email: {}", request.getEmail());
        if (userRepo.existsByEmail(request.getEmail())) {
            log.error(TAG + "User with email {} is already exists", request.getEmail());
            return false;
        }
        User user = UserMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);
        log.info(TAG + "User with email {} was successfully created", request.getEmail());
        return true;
    }



    public boolean changePassword(PasswordDto dto, CustomPrincipal principal) {
        log.info(TAG + "Change password for user with email: {}", principal.getName());
        User user = userRepo.getUserByEmail(principal.getName()).orElse(null);
        assert user != null;
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) return false;
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);
        return true;
    }


    public void changePasswordForgot(String email, String password) {
        log.info(TAG + "Change password for user: {}", email);
        User user = userRepo.getUserByEmail(email).orElse(null);
        if (user == null) return;
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        emailService.passwordWasChanged(user.getEmail());
    }




    public boolean existsByEmail(String email){
        log.info(TAG + "Exists by email: {}", email);
        return userRepo.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        log.info(TAG + "Get by email: {}", email);
        return userRepo.getUserByEmail(email).orElse(null);
    }

    public User getById(Long userId) {
        log.info(TAG + "Exists by user id: {}", userId);
        return userRepo.findById(userId).orElse(null);
        }



}
