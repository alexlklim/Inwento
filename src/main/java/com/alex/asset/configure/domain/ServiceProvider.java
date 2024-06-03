package com.alex.asset.configure.domain;


import com.alex.asset.utils.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "service_provider")
public class ServiceProvider extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "company", unique = true, nullable = false)
    String company;

    @Column(name = "nip", unique = true, nullable = false)
    String nip;

    @Column(name = "address", unique = true, nullable = false)
    String address;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    List<ContactPerson> contactPersons;
}
