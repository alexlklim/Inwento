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
    @PutMapping("/read")
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


    @Operation(summary = "Send notification to specific users")
    @Secured("ROLE_ADMIN")
    @PostMapping("/notify")
    @ResponseStatus(HttpStatus.OK)
    public void sendNotificationToUsers(
            @RequestBody NotificationDto notificationDto) {
        log.info(TAG + "Send notification to users");
        notificationService.sendNotificationToUsers(
                notificationDto,
                SecHolder.getUserId());
    }





}
