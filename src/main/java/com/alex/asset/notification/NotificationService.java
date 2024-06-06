package com.alex.asset.notification;


import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.domain.Notification;
import com.alex.asset.notification.domain.NotificationDto;
import com.alex.asset.notification.domain.Reason;
import com.alex.asset.security.domain.Role;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import com.alex.asset.utils.dto.DtoActive;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
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
        notificationRepo.getNotViewedNotifications(userRepo.getUser(userId))
                .stream()
                .peek(notification -> notification.setViewed(true))
                .forEach(notificationRepo::save);
    }


    public void readNotification(Long userId, Long notificationId) {
        log.info(TAG + "Read notification with id {} by user with id {}", notificationId, userId);
        Notification notification = notificationRepo.getNotificationByIdAndUser(notificationId, userRepo.getUser(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setViewed(true);
        notificationRepo.save(notification);
    }



    public void sendSystemNotificationToSpecificUser(Reason reason, User userTo) {
        sendSystemNotificationToSpecificUser(reason, userTo, "System message", null);
    }


    public void sendSystemNotificationToSpecificUser(Reason reason, User userTo, String message, User userFrom) {
        log.info(TAG + "Send system notification to user with id {}", userTo.getId());
        Notification notification = new Notification();
        notification.setActive(true);
        notification.setViewed(false);
        notification.setReason(reason);
        notification.setMessage(message);
        notification.setUser(userTo);
        notification.setCreatedBy(userFrom);
        notificationRepo.save(notification);
    }



    public void sendSystemNotificationToAllUsers(Reason reason) {
        log.info(TAG + "Send system notification to all users");
        List<User> users = userRepo.getActiveUsers();
        users.forEach(user -> sendSystemNotificationToSpecificUser(reason, user));
    }



    @SneakyThrows
    public void sendNotificationToUsers(NotificationDto notificationDto, Long userId) {
        User userFrom = userRepo.getUser(userId);
        for (Long id : notificationDto.getUserIds()){
            User userTo = userRepo.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("User not found with id " + id));
            sendSystemNotificationToSpecificUser(
                    Reason.fromString(notificationDto.getReason()),
                    userTo,
                    notificationDto.getMessage(),
                    userFrom);
        }

    }
}
