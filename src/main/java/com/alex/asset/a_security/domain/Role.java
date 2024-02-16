package com.alex.asset.a_security.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {

    ADMIN("ROLE_ADMIN", "ROLE_CLIENT", "ROLE_EMP"),
    CLIENT("ROLE_CLIENT", "ROLE_EMP"),
    EMP("ROLE_EMP");
    private final List<String> permissions;

    Role(String... permissions) {
        this.permissions = Arrays.asList(permissions);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(getPermissions()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
