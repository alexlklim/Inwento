package com.alex.asset.invents.domain;


import com.alex.asset.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "invents")
public class Invent extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "finish_date")
    LocalDate finishDate;

    String info;


}
