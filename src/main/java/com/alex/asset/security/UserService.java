package com.alex.asset.security;

import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.dto.UserDto;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.exceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final String TAG = "USER_SERVICE - ";
    private final UserRepo userRepo;
    private final LogService logService;
    private final NotificationService notificationService;

    public UserDto getInfoAboutUserById(Long id) {
        return UserMapper.toDto(userRepo.getUser(id));
    }

    public List<UserDto> getAllUsers() {
        log.info(TAG + "Get all users");
        return userRepo.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());

    }


    @SneakyThrows
    @Modifying
    @Transactional
    public void changeUserVisibility(DtoActive dto, Long userId) {
        log.info(TAG + "Change user visibility");
        User user = userRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + dto.getId() + " was not found"));
        user.setActive(dto.isActive());
        userRepo.save(user);

        logService.addLog(userId, Action.UPDATE, Section.USERS, dto.toString());
        if (dto.isActive()) {
            notificationService.sendSystemNotificationToSpecificUser(Reason.USER_WAS_ENABLED, userRepo.getUser(userId));
            notificationService.sendSystemNotificationToSpecificUser(Reason.YOU_WERE_ENABLED, user);
        }
        notificationService.sendSystemNotificationToSpecificUser(Reason.USER_WAS_DISABLED, userRepo.getUser(userId));
        notificationService.sendSystemNotificationToSpecificUser(Reason.YOU_WERE_DISABLED, user);
    }


    public UserDto updateUser(Long id, UserDto dto, Long userId) {
        log.error(TAG + "Update user with id {} by user with id {}", id, userId);
        User user = userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found"));
        user.setFirstname(dto.getFirstName());
        user.setLastname(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRoles(Role.fromString(dto.getRole()));
        logService.addLog(userId, Action.UPDATE, Section.USERS, dto.toString());
        notificationService.sendSystemNotificationToSpecificUser(Reason.USER_WAS_UPDATED, userRepo.getUser(userId));
        notificationService.sendSystemNotificationToSpecificUser(Reason.YOU_WERE_UPDATED, user);
        return UserMapper.toDto(userRepo.save(user));
    }


}