package com.alex.asset.utils;

import com.alex.asset.security.config.jwt.CustomPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecHolder {

    public static Long getUserId() {
        return ((CustomPrincipal)
                (SecurityContextHolder.getContext().getAuthentication())
                        .getPrincipal()).getUserId();
    }

    public static CustomPrincipal getPrincipal() {
        return ((CustomPrincipal)
                (SecurityContextHolder.getContext().getAuthentication())
                        .getPrincipal());
    }
}
