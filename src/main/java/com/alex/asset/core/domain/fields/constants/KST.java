package com.alex.asset.core.domain.fields.constants;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ksts")
public class KST {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String kst;
}
