package com.example.shop.security;

import com.example.shop.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private final Role role;

    public Authority(Role role){
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
