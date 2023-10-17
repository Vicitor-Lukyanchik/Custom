package com.example.custom.jwt;


import com.example.custom.entity.Role;
import com.example.custom.entity.Status;
import com.example.custom.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(new ArrayList<>(user.getRoles())))
                .enabled(user.getStatus().equals(Status.ACTIVE))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
