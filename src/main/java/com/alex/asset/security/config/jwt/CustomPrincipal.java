package com.alex.asset.security.config.jwt;

import com.alex.asset.security.domain.User;
import lombok.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data @Getter @Setter @Builder(toBuilder = true) @NoArgsConstructor @AllArgsConstructor
public class CustomPrincipal implements Principal {
    private UUID userUUID;
    private String email;
    private List<String> roles;
    private UUID comapnyUUID;
    @Override
    public String getName() {
        return null;
    }

    public CustomPrincipal(User user) {
        this.userUUID = user.getId();
        this.email = user.getEmail();
        this.comapnyUUID = user.getCompanyUUID();
        this.roles = Collections.singletonList(user.getRoles().name()); // Assuming roles is an Enum
    }




}
