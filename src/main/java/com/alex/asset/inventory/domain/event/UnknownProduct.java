package com.alex.asset.inventory.domain.event;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "unknown_products")
public class UnknownProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Id
    @Column(length = 255)
    private String code;
}
