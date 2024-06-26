package com.alex.asset.inventory.domain.event;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonBackReference
    Branch branch;

    String info;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    @JsonBackReference
    Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    User user;

}
