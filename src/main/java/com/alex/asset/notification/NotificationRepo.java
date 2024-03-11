package com.alex.asset.notification;

import com.alex.asset.notification.domain.Notification;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    Optional<Notification> getNotificationByIdAndUser(Long id, User user);

    List<Notification> getNotificationsByUser(User user);





    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.user = ?1")
    void readAllNotifications( User user);




}
