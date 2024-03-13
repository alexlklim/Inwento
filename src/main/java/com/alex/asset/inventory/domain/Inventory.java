package com.alex.asset.inventory.domain;


import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "inventories")
public class Inventory extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "finish_date")
    LocalDate finishDate;

    @Column(name = "is_finished")
    boolean isFinished;

    String info;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;


}
