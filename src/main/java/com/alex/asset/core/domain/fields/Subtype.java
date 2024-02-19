package com.alex.asset.core.domain.fields;

import com.alex.asset.utils.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subtypes")
public class Subtype  extends BaseEntity {
    private String subtype;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

}
