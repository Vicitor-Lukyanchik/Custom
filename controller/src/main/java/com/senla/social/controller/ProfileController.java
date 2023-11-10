package com.senla.social.controller;

import com.senla.social.authentificate.ProfileAuthenticator;
import com.senla.social.converter.ProfileConverter;
import com.senla.social.dto.MessageDto;
import com.senla.social.dto.ProfileDto;
import com.senla.social.entity.Group;
import com.senla.social.entity.Profile;
import com.senla.social.service.GroupService;
import com.senla.social.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileAuthenticator authenticator;
    private final ProfileService profileService;
    private final ProfileConverter profileConvertor;
    private final GroupService groupService;

    @PutMapping
    public ResponseEntity<MessageDto> update(@RequestBody @Valid ProfileDto profileDto) {
        Profile profile = authenticator.getProfile();

        profileService.update(profileConvertor.convertToEntity(profileDto, profile));
        return ResponseEntity.ok(new MessageDto("Profile have been updated"));
    }

    @PutMapping("/groups")
    public ResponseEntity<MessageDto> joinGroup(@RequestParam Long groupId) {
        Group group = groupService.findById(groupId);
        Profile profile = authenticator.getProfile();
        profile.getGroups().add(group);
        profileService.update(profile);
        return ResponseEntity.ok(new MessageDto("Profile have been joined in group"));
    }
}
