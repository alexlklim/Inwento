package com.alex.asset.configure.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subtypes")
@Schema(description = "Subtype")
public class Subtype extends BaseEntityActive {


    @Schema(description = "Subtype", example = "Computers")
    String subtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference @JsonIgnore
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @Schema(description = "Type", example = "Office Equipment")
    private Type type;

    public Subtype(String subtype, Type type) {
        this.subtype = subtype;
        this.type = type;
    }
}
