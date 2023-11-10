package com.senla.social.controller;

import com.senla.social.authentificate.ProfileAuthenticator;
import com.senla.social.converter.MessageConverter;
import com.senla.social.dto.message.AllMessageDto;
import com.senla.social.dto.MessageDto;
import com.senla.social.dto.message.MessageSendDto;
import com.senla.social.entity.Message;
import com.senla.social.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final ProfileAuthenticator authenticator;
    private final ProfileService profileService;
    private final MessageConverter messageConvertor;

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@RequestBody @Valid MessageSendDto messageSendDto) {
        profileService.sendMessage(authenticator.getProfile(), messageSendDto.getReceiverId(), messageSendDto.getText());
        return ResponseEntity.ok(new MessageDto("Message have been sent"));
    }

    @GetMapping
    public ResponseEntity<List<AllMessageDto>> findAll(@RequestParam Long friendId) {
        Map<Message, String> messages = profileService.findAllMessages(authenticator.getProfile(), friendId);
        List<AllMessageDto> result = new ArrayList<>();
        for (Map.Entry<Message, String> message : messages.entrySet()) {
            result.add(messageConvertor.convert(message.getKey(), message.getValue()));
        }

        return ResponseEntity.ok(result);
    }
}
