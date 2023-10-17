package com.example.custom.converter;

import com.example.custom.dto.UserDto;
import com.example.custom.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .id(user.getId()).roles(user.getRoles())
                .password(user.getPassword()).message("")
                .status(user.getStatus()).build();
    }
}
