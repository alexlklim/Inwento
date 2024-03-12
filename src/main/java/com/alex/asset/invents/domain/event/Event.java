package com.alex.asset.invents.domain.event;


import com.alex.asset.configure.domain.Branch;
import com.alex.asset.invents.domain.Invent;
import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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

    @ManyToOne @JoinColumn(name = "invent_id") @JsonBackReference
    Invent invent;

    @ManyToOne @JoinColumn(name = "user_id") @JsonBackReference
    User user;

    @ManyToOne @JoinColumn(name = "branch_id") @JsonBackReference
    Branch branch;

    String info;

    @ManyToMany
    @JoinTable(
            name = "event_products",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> products;


    @ElementCollection
    @CollectionTable(
            name = "unknown_products",
            joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "code")
    List<String> unknownProducts;


}
