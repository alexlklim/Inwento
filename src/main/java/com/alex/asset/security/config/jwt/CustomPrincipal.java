package com.alex.asset.security.config.jwt;

import com.alex.asset.security.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data @Getter @Setter @Builder(toBuilder = true) @NoArgsConstructor @AllArgsConstructor
public class CustomPrincipal implements Principal {
    private String name;
    private Long userId;
    private Long companyId;
    @Override
    public String getName() {
        return name;
    }

    public CustomPrincipal(User user) {
        this.userId = user.getId();
        this.name = user.getEmail();
        this.companyId = user.getCompany().getId();
    }
}
