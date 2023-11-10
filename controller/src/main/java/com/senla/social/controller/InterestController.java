package com.senla.social.controller;

import com.senla.social.dto.InterestDto;
import com.senla.social.dto.MessageDto;
import com.senla.social.entity.Interest;
import com.senla.social.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/interests/admin")
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<MessageDto> createInterest(@Valid @RequestBody InterestDto interestDto) {
        interestService.create(new Interest(interestDto.getName()));
        return ResponseEntity.ok(new MessageDto("Interest have been created"));
    }
}
