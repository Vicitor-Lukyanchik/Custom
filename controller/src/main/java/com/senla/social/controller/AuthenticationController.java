package com.senla.social.controller;

import com.senla.social.dto.MessageDto;
import com.senla.social.dto.authentication.AuthenticationRequestDto;
import com.senla.social.dto.authentication.RegistrationRequestDto;
import com.senla.social.entity.Profile;
import com.senla.social.entity.User;
import com.senla.social.jwt.JwtTokenProvider;
import com.senla.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegistrationRequestDto requestDto) {
        try {
            User user = new User(requestDto.getUsername(), requestDto.getPassword());

            Profile profile = new Profile(requestDto.getFirstname(), requestDto.getLastname(),
                    requestDto.getEmail(), requestDto.getAge());

            User registeredUser = userService.register(user, profile);

            String token = jwtTokenProvider.createToken(registeredUser.getUsername(), registeredUser.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", registeredUser.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/group")
    public ResponseEntity<MessageDto> addGroupRole() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.updateRoleGroupManager(user);
        return ResponseEntity.ok(new MessageDto("Role group manager have been added"));
    }
}
