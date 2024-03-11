package com.alex.asset.notification;


import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.utils.dto.DtoActive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<NotificationDto>> getNotifications(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Get all notification for user with id {}", principal.getUserId());
        return new ResponseEntity<>(notificationService.getNotifications(principal.getUserId()), HttpStatus.OK);
    }


    @Operation(summary = "Read all notification")
    @GetMapping("/read")
    public ResponseEntity<HttpStatus> readAllNotifications(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Read all notifications by user with id {}", principal.getUserId());

        notificationService.readAllNotifications(principal.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @Operation(summary = "Read notification by id")
    @PutMapping("/read/{id}")
    public ResponseEntity<HttpStatus> readNotificationById(
            @PathVariable("id") Long notificationId,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Read notification with id {} by user with id {}", notificationId, principal.getUserId());
        notificationService.readNotification(principal.getUserId(), notificationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @Operation(summary = "Send notification to specific user")
    @Secured("ROLE_ADMIN")
    @PostMapping("/notify/{id}")
    public ResponseEntity<HttpStatus> sendNotificationToSpecificUser(
            @PathVariable("id") Long userToId,
            @RequestBody NotificationDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Send notification to user with id {} from user with id {}", userToId, principal.getUserId());
        notificationService.saveNotificationToSpecificUser(dto, userToId, principal.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Send notification to all users")
    @Secured("ROLE_ADMIN")
    @PostMapping("/notify")
    public ResponseEntity<HttpStatus> sendNotificationToAllUsers(
            @RequestBody NotificationDto dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Send notification to all users from user with id {}", principal.getUserId());

        notificationService.saveNotificationToAllUsers(dto, principal.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Change visibility of notification")
    @Secured("ROLE_ADMIN")
    @PutMapping("/active")
    public ResponseEntity<HttpStatus> changeVisibilityOfNotification(
            @RequestBody DtoActive dto,
            Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.info(TAG + "Change visibility of notification with id {} to status {}", dto.getId(), dto.isActive());
        notificationService.changeNotificationVisibility(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
