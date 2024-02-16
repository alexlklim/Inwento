package com.alex.asset.a_security.jwt;

import com.alex.asset.a_security.domain.User;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Data @Getter @Setter @Builder(toBuilder = true)
@NoArgsConstructor @AllArgsConstructor
public class CustomPrincipal implements Principal {
    private UUID id;
    private List<SimpleGrantedAuthority> roles;
    private Long companyId;
    @Override
    public String getName() {
        return null;
    }

    public CustomPrincipal(User user) {
        this.id = user.getId();
        this.roles = user.getRoles().getAuthorities();
        this.companyId = user.getCompanyId();
    }

}