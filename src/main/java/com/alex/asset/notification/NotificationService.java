package com.alex.asset.notification;


import com.alex.asset.notification.domain.Notification;
import com.alex.asset.notification.domain.NotificationDto;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.utils.expceptions.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final String TAG = "NOTIFICATION_SERVICE - ";

    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;
    private final UserRepo userRepo;
    public List<NotificationDto> getNotifications(Long userId) {
        log.info(TAG + "get notifications for user with id {}", userId);
        return notificationRepo.getNotificationsByUser(userRepo.getUser(userId))
                .stream()
                .map(notificationMapper::mapToDto)
                .collect(Collectors.toList());

    }



    public void readAllNotifications(Long userId) {
        log.info(TAG + "Read all notifications for user with id {}", userId);
        notificationRepo.readAllNotifications(userRepo.getUser(userId));
    }


    @SneakyThrows
    public void readNotification(Long userId, Long notificationId) {
        log.info(TAG + "Read notification with id {} by user with id {}", notificationId, userId);
        Notification notification = notificationRepo.getNotificationByIdAndUser(notificationId, userRepo.getUser(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setRead(true);
        notificationRepo.save(notification);
    }

    @SneakyThrows
    public void saveNotificationToSpecificUser(NotificationDto dto, Long userTo, Long userFromId) {
        log.info(TAG + "Send notification to specific user with id {} from user with id {}", userTo, userFromId);
        notificationRepo.save(notificationMapper
                .mapToEntity(
                        dto,
                        userRepo.findById(userTo).orElseThrow(() -> new ResourceNotFoundException("User with id " + userTo + " not found")),
                        userRepo.getUser(userFromId)));

    }

    @SneakyThrows
    public void saveNotificationToAllUsers(NotificationDto dto, Long userFromId) {
        log.info(TAG + "Send notification to all users from user with id {}", userFromId);
        List<User> users = userRepo.getActiveUsers();
        users.forEach(user -> saveNotificationToSpecificUser(dto, user.getId(), userFromId));
    }




    public void sendSystemNotificationToSpecificUser(Reason reason, User user){
        log.info(TAG + "Send system notification to use with id {}", user.getId());
        Notification notification = new Notification();
        notification.setActive(true);
        notification.setRead(false);
        notification.setReason(reason);
        notification.setMessage("System message");
        notification.setUser(user);
        notificationRepo.save(notification);
    }



    public void sendSystemNotificationToAllUsers(Reason reason){
        log.info(TAG + "Send system notification to all users");
        sendSystemNotificationToSpecificUser(reason, null);
        List<User> users = userRepo.getActiveUsers();
        users.forEach(user ->  sendSystemNotificationToSpecificUser(reason, user));
    }

    public void changeNotificationVisibility(DtoActive dto) {
        log.info(TAG + "Change notification visibility with id {} to status {}", dto.getId(), dto.isActive());
        Notification notification = notificationRepo.findById(dto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Notification with id "+ dto.getId() + " not found"));
        notification.setActive(dto.isActive());
        notificationRepo.save(notification);
    }
}
