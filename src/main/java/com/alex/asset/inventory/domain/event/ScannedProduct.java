package com.alex.asset.inventory.domain.event;


import com.alex.asset.product.domain.Product;
import com.alex.asset.security.domain.User;
import com.alex.asset.utils.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Entity @Table(name = "scanned_products")
public class ScannedProduct extends BaseEntity {

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

    @Column(name = "is_scanned")
    Boolean isScanned;

    @Override
    public String toString() {
        return "ScannedProduct{" +
                "product=" + product.getTitle() +
                ", event=" + event.getId() +
                ", isScanned=" + isScanned +
                '}';
    }
}
