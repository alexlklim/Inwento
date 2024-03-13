package com.alex.asset.configure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ksts")
@Schema(description = "KST")
public class KST extends BaseEntityActive {

    @Schema(description = "Number", example = "015")
    String num;

    @Schema(description = "City", example = "Grunty orne")
    String kst;
}
