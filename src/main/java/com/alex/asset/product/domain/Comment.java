package com.alex.asset.product.domain;


import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @CreatedDate
    @Column(name = "created")
    LocalDateTime created;

    String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    Product product;
}
