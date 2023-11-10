package com.senla.social.service;

import com.senla.social.entity.Friends;
import com.senla.social.entity.Profile;

import java.util.List;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface FriendsService {

    Friends create(Friends friends);

    List<Friends> findAllBySender(Profile sender);

    Friends findById(Long id);
}
