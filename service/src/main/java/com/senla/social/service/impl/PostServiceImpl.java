package com.senla.social.service.impl;

import com.senla.social.entity.Group;
import com.senla.social.entity.Post;
import com.senla.social.entity.Profile;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.PostRepository;
import com.senla.social.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    /**
     * For work with post database
     */
    private final PostRepository postRepository;

    /**
     * For creating new post
     * @param post that will create
     */
    @Override
    @Transactional
    public void create(@Valid Post post) {
        Post createdPost = postRepository.save(post);
        log.info("In create - post successfully have been created by id : {}", createdPost.getId());
    }

    /**
     * For updating post by sender
     * @param post that will update
     */
    @Override
    @Transactional
    public void update(@Valid Post post) {
        Optional<Post> validatedPost = postRepository.findById(post.getId());
        if (validatedPost.isEmpty()) {
            log.warn("In update - post haven't been updated by id : {}", post.getId());
            throw new ServiceException("Post with id " + post.getId() + " doesn't exist");
        }
        validateGroupPost(validatedPost.get(), post.getGroup().getId());
        Post updatedPost = postRepository.save(post);
        log.info("In update - post successfully have been updated by id : {}", updatedPost.getId());
    }

    /**
     * For deleting post
     * @param id to find post and delete
     * @param group to find at what group delete post
     */
    @Override
    @Transactional
    public void delete(Long id, @Valid Group group) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            log.warn("In delete - post haven't been deleted by id : {}", id);
            throw new ServiceException("Post with id " + id + " doesn't exist");
        }
        validateGroupPost(post.get(), group.getId());
        postRepository.delete(post.get());
        log.info("In delete - post successfully have been deleted by id : {}", id);
    }

    private void validateGroupPost(Post post, Long groupId) {
        if (!post.getGroup().getId().equals(groupId)) {
            throw new ServiceException("In this group there isn't post with id : " + post.getId());
        }
    }

    /**
     * For finding all posts by group
     * @param group to find posts
     * @param profile get group
     * @return all post from group
     */
    @Override
    @Transactional
    public List<Post> findAllByGroup(Group group, Profile profile) {
        profile.getGroups().stream()
                .filter(g -> g.getId().equals(group.getId()))
                .findFirst()
                .orElseThrow(() ->
                {throw new ServiceException("Profile with id " + profile.getId() + " do not in group with id " + group.getId());});

        List<Post> posts = postRepository.findAllByGroup(group);

        log.info("In findAll - post successfully have been founded, size : {}", posts.size());
        return posts;
    }
}
