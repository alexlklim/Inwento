package com.alex.asset.notification.domain;


import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "is_read")
    boolean isRead;

    @Enumerated(EnumType.STRING)
    private Reason reason;

    String message;


    @ManyToOne @JoinColumn(name = "to_user_id")
    User user;

    @ManyToOne @JoinColumn(name = "created_by")
    User createdBy;

}
