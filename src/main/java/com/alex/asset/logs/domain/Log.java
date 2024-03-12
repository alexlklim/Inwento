package com.alex.asset.logs.domain;


import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "logs") @Entity
public class Log {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore @CreatedDate @Column(name = "created")
    LocalDateTime created;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;


    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column(name = "section")
    private Section section;

    String text;


}
