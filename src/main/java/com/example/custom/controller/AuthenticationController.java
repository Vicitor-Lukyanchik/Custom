package com.example.custom.controller;

import com.example.custom.dto.UserDto;
import com.example.custom.dto.authentication.AuthenticationRequestDto;
import com.example.custom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginTemplate(@ModelAttribute("login") AuthenticationRequestDto requestDto) {
        return "authentication/login1";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes) {
        UserDto userDto = userService.login(requestDto);
        if (userDto.getMessage().isEmpty()) {
            return "redirect:/chops";
        } else {
            redirectAttributes.addFlashAttribute("isNotLogin", true);
            redirectAttributes.addFlashAttribute("errorMessage", userDto.getMessage());
            return "redirect:/auth/login";
        }
    }
}
