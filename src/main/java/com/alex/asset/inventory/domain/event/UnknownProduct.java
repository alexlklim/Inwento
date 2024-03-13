package com.alex.asset.inventory.domain.event;

import jakarta.persistence.*;
import lombok.*;

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
    private String code;
}
