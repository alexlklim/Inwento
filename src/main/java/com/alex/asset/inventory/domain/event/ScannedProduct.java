package com.alex.asset.inventory.domain.event;


import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@EntityListeners(AuditingEntityListener.class)
@Entity @Table(name = "scanned_products")
public class ScannedProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore @CreatedDate @Column(name = "created")
    LocalDateTime created;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    Product product;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference
    Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User user;
}
