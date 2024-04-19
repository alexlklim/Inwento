package com.alex.asset.notification;


import com.alex.asset.notification.domain.NotificationDto;
import com.alex.asset.utils.SecHolder;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@Tag(name = "Notification Controller", description = "Notification API")
public class NotificationController {

    private final String TAG = "NOTIFICATION_CONTROLLER - ";

    private final NotificationService notificationService;


    @Operation(summary = "Get all notification for authorized user")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationDto> getNotifications() {
        log.info(TAG + "Get all notifications");
        return notificationService.getNotifications(SecHolder.getUserId());
    }


    @Operation(summary = "Read all notification")
    @GetMapping("/read")
    @ResponseStatus(HttpStatus.OK)
    public void readAllNotifications() {
        log.info(TAG + "Read all notifications");
        notificationService.readAllNotifications(SecHolder.getUserId());
    }


    @Operation(summary = "Read notification by id")
    @PutMapping("/read/{notification_id}")
    @ResponseStatus(HttpStatus.OK)
    public void readNotificationById(
            @PathVariable("notification_id") Long notificationId) {
        log.info(TAG + "Read notification with id {}", notificationId);
        notificationService.readNotification(
                SecHolder.getUserId(),
                notificationId);
    }


    @Operation(summary = "Send notification to specific user")
    @Secured("ROLE_ADMIN")
    @PostMapping("/notify/{user_to_id}")
    @ResponseStatus(HttpStatus.OK)
    public void sendNotificationToSpecificUser(
            @PathVariable("user_to_id") Long userToId,
            @RequestBody NotificationDto notificationDto) {
        log.info(TAG + "Send notification to user with id {}", userToId);
        notificationService.saveNotificationToSpecificUser(
                notificationDto,
                userToId,
                SecHolder.getUserId());
    }

    @Operation(summary = "Send notification to all users")
    @Secured("ROLE_ADMIN")
    @PostMapping("/notify")
    @ResponseStatus(HttpStatus.OK)
    public void sendNotificationToAllUsers(
            @RequestBody NotificationDto notificationDto) {
        log.info(TAG + "Send notification to all users");
        notificationService.saveNotificationToAllUsers(
                notificationDto,
                SecHolder.getUserId());
    }


    @Operation(summary = "Change visibility of notification")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public void changeVisibilityOfNotification(
            @RequestBody DtoActive activeDto) {
        log.info(TAG + "Change visibility of notification with id {} to status {}",
                activeDto.getId(), activeDto.isActive());
        notificationService.changeNotificationVisibility(
                activeDto,
                SecHolder.getUserId());
    }
}
