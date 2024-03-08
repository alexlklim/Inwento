package com.alex.asset.configure.domain;

import com.alex.asset.utils.BaseEntityActive;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ksts")
public class KST extends BaseEntityActive {

    String num;

    String kst;
}
