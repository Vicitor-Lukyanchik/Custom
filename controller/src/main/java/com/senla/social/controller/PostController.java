package com.senla.social.controller;

import com.senla.social.authentificate.ProfileAuthenticator;
import com.senla.social.dto.MessageDto;
import com.senla.social.dto.post.PostDto;
import com.senla.social.dto.post.PostUpdateDto;
import com.senla.social.entity.Post;
import com.senla.social.entity.Profile;
import com.senla.social.service.GroupService;
import com.senla.social.service.PostService;
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
@RequestMapping(value = "/groups/posts/manager")
public class PostController {

    private final PostService postService;
    private final GroupService groupService;
    private final ProfileAuthenticator authenticator;

    @PostMapping
    public ResponseEntity<MessageDto> createPost(@Valid @RequestBody PostDto postDto) {
        Profile profile = authenticator.getProfile();
        Post post = new Post(groupService.findById(profile.getId()), postDto.getTitle(), postDto.getText());
        postService.create(post);
        return ResponseEntity.ok(new MessageDto("Post have been created"));
    }

    @PutMapping
    public ResponseEntity<MessageDto> updatePost(@Valid @RequestBody PostUpdateDto postDto) {
        Profile profile = authenticator.getProfile();
        Post post = new Post(postDto.getId(), groupService.findById(profile.getId()), postDto.getTitle(), postDto.getText());
        postService.update(post);
        return ResponseEntity.ok(new MessageDto("Post have been created"));
    }

    @DeleteMapping
    public ResponseEntity<MessageDto> deletePost(@RequestParam Long id){
        postService.delete(id, groupService.findById(authenticator.getProfile().getId()));
        return ResponseEntity.ok(new MessageDto("Post have been deleted"));
    }
}
