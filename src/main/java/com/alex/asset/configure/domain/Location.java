package com.alex.asset.configure.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "locations")
@Schema(description = "Locations")
public class Location extends BaseEntityActive {

    @Schema(description = "location", example = "Pokój prezesa")
    String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @Schema(description = "Type", example = "Office Equipment")
    Branch branch;

    public Location(String location, Branch branch) {
        this.location = location;
        this.branch = branch;
    }


}
