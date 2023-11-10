package com.senla.social.service;

import com.senla.social.entity.Group;
import com.senla.social.entity.Post;
import com.senla.social.entity.Profile;

import java.util.List;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface PostService {

    void create(Post post);

    void update(Post post);

    void delete(Long id, Group group);

    List<Post> findAllByGroup(Group group, Profile profile);
}
