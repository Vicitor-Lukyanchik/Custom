package com.senla.social.service;

import com.senla.social.entity.Profile;
import com.senla.social.entity.User;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface UserService {

    User register(User user, Profile profile);

    User findByUsername(String username);

    User findById(Long id);

    User updateRoleGroupManager(User user);
}
