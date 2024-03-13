package com.alex.asset.security.config.jwt;

import com.alex.asset.email.EmailService;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.UserMapper;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.dto.PasswordDto;
import com.alex.asset.security.dto.RegisterDto;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.expceptions.errors.UserAlreadyExistException;
import com.alex.asset.utils.expceptions.errors.UserNotRegisterYet;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {
    private final String TAG = "USER AUTHENTICATION SERVICE - ";

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final EmailService emailService;
    private final LogService logService;
    private final NotificationService notificationService;


    @SneakyThrows
    public void register(RegisterDto dto, Long userId) {
        log.info(TAG + "Register user with email: {}", dto.getEmail());
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException("User with email {} is already exists");
        }
        User user = UserMapper.toUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepo.save(user);
        emailService.accountWasCreated(user);
        logService.addLog(userId, Action.CREATE, Section.USERS, dto.toString());
        notificationService.sendSystemNotificationToSpecificUser(Reason.USER_WAS_CREATED, userRepo.getUser(userId));
        notificationService.sendSystemNotificationToSpecificUser(Reason.NEW_USER, user);
    }


    @SneakyThrows
    public void changePassword(PasswordDto dto, CustomPrincipal principal) {
        log.info(TAG + "Change password for user with email: {}", principal.getName());
        User user = userRepo.getUser(principal.getUserId());
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword()))
            throw new AuthenticationException("Password is wrong");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);
        logService.addLog(principal.getUserId(), Action.UPDATE, Section.USERS, "Change password");
        notificationService.sendSystemNotificationToSpecificUser(Reason.PASSWORD_WAS_CHANGED, user);

    }


    @SneakyThrows
    public void changePasswordForgot(String email, String password) {
        log.info(TAG + "Change password for user: {}", email);
        User user = userRepo.getUserByEmail(email).orElseThrow(
                () -> new UserNotRegisterYet("User with email " + email + " is not registered"));
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        logService.addLog(user.getId(), Action.UPDATE, Section.USERS, "Change password");
        notificationService.sendSystemNotificationToSpecificUser(Reason.PASSWORD_WAS_CHANGED, user);
    }


    public boolean existsByEmail(String email) {
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

    @Transactional
    public void updateLastActivity(Long userId) {
        User user = userRepo.getUser(userId);
        user.setLastActivity(LocalDateTime.now());
        userRepo.save(user);
    }

}
