package com.example.custom.service.impl;

import com.example.custom.converter.UserToDtoConverter;
import com.example.custom.dto.UserDto;
import com.example.custom.dto.authentication.AuthenticationRequestDto;
import com.example.custom.entity.User;
import com.example.custom.jwt.JwtTokenProvider;
import com.example.custom.repository.UserRepository;
import com.example.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserToDtoConverter userToDtoConverter;

    @Override
    @Transactional
    public UserDto login(AuthenticationRequestDto requestDto) {
            String username = requestDto.getUsername();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        } catch (AuthenticationException e) {
            return UserDto.builder().message("Invalid password").build();
        }
        UserDto userDto = findByUsername(username);
            jwtTokenProvider.createToken(username, userDto.getRoles());
            return userDto;
    }
    @Override
    public UserDto findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        if (!result.isPresent()){
            return UserDto.builder().message("User haven't founded by username : " + username).build();
        }
        return userToDtoConverter.convert(result.get());
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        if (!result.isPresent()){
            return UserDto.builder().message("User haven't founded by id : " + id).build();
        }
        return userToDtoConverter.convert(result.get());
    }
}
