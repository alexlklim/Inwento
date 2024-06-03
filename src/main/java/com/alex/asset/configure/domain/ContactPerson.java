package com.alex.asset.configure.domain;

import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "contact_person")
public class ContactPerson extends BaseEntity {

    @Column(name = "is_active")
    boolean isActive;

    @Column(name = "firstname", unique = true, nullable = false)
    String firstname;

    @Column(name = "lastname", unique = true, nullable = false)
    String lastname;

    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;

    @Column(name = "email", unique = true, nullable = false)
    String email;


    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    @JsonIgnore
    ServiceProvider serviceProvider;
}
