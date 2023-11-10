package com.senla.social.controller;

import com.senla.social.authentificate.ProfileAuthenticator;
import com.senla.social.converter.PostConverter;
import com.senla.social.dto.GroupDto;
import com.senla.social.dto.MessageDto;
import com.senla.social.dto.post.PostDto;
import com.senla.social.entity.Group;
import com.senla.social.entity.Post;
import com.senla.social.entity.Profile;
import com.senla.social.service.GroupService;
import com.senla.social.service.InterestService;
import com.senla.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/groups")
public class GroupController {

    private final GroupService groupService;
    private final ProfileAuthenticator authenticator;
    private final InterestService interestService;
    private final PostService postService;
    private final PostConverter postConverter;

    @PostMapping("/manager")
    public ResponseEntity<MessageDto> createGroup(@Valid @RequestBody GroupDto groupDto) {
        Profile profile = authenticator.getProfile();
        Group group = new Group(profile.getId(), groupDto.getName(), interestService.findByName(groupDto.getInterest()));
        groupService.create(group);
        return ResponseEntity.ok(new MessageDto("Group have been created"));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> findGroupPosts(@RequestParam Long groupId) {
        Profile profile = authenticator.getProfile();
        List<Post> posts = postService.findAllByGroup(groupService.findById(groupId), profile);
        return ResponseEntity.ok(posts.stream()
                .map(postConverter::convert)
                .collect(Collectors.toList()));
    }
}
