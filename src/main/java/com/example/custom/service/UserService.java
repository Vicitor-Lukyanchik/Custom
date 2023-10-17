package com.example.custom.service;

import com.example.custom.dto.UserDto;
import com.example.custom.dto.authentication.AuthenticationRequestDto;
import com.example.custom.entity.User;

import javax.validation.Valid;

public interface UserService {

    UserDto login(AuthenticationRequestDto requestDto);

    UserDto findByUsername(String username);

    UserDto findById(Long id);
}
