package com.alex.asset.product.domain;


import com.alex.asset.configure.domain.ContactPerson;
import com.alex.asset.configure.domain.Delivery;
import com.alex.asset.configure.domain.ServiceProvider;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "serviced_assets")
public class ServicedAsset extends BaseEntity {

    @Column(name = "service_start_date")
    LocalDate serviceStartDate;

    @Column(name = "service_end_date")
    LocalDate serviceEndDate;

    @Column(name = "planned_service_period")
    LocalDate plannedServicePeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery", nullable = false)
    Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", nullable = false)
    ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "contact_person_id", nullable = false)
    ContactPerson contactPerson;

    @ManyToOne
    @JoinColumn(name = "send_by_id", nullable = false)
    User sendBy;

    @ManyToOne
    @JoinColumn(name = "received_by_id", nullable = false)
    User receivedBy;
}
