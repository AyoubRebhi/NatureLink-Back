package com.example.naturelink.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN , EMPLOYEE ;
    @Override
    public String getAuthority() {
        return "ROLE_" + name();  // Prefix roles with 'ROLE_' as Spring Security expects this convention
    }
}
