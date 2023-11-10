package com.senla.social.service.impl;

import com.senla.social.entity.Profile;
import com.senla.social.entity.Role;
import com.senla.social.entity.Status;
import com.senla.social.entity.User;
import com.senla.social.exception.ServiceException;
import com.senla.social.exception.UserNotFoundException;
import com.senla.social.repository.ProfileRepository;
import com.senla.social.repository.RoleRepository;
import com.senla.social.repository.UserRepository;
import com.senla.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
@Validated
public class UserServiceImpl implements UserService {

    private static final String NOT_SPECIFIED = "not specified";
    private static final String TOWN_DEFAULT = "Unknown";
    private static final String GROUP_MANAGER = "ROLE_GROUP_MANAGER";

    /**
     * For work with profile database
     */
    private final ProfileRepository profileRepository;
    /**
     * For work with user database
     */
    private final UserRepository userRepository;
    /**
     * For work with role database
     */
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * For registration user and create profile
     * @param user to register user
     * @param profile to create profile
     * @return user that have been registered
     */
    @Override
    @Transactional
    public User register(@Valid User user, @Valid Profile profile) {
        if (isUsernameUsed(user.getUsername())) {
            log.warn("IN register - haven't registered user with username : {}", user.getUsername());
            throw new ServiceException("Username is already exists");
        }

        User registeredUser = userRepository.save(buildUser(user));
        profileRepository.save(buildProfile(profile, registeredUser));

        log.info("IN register - user successfully have been registered with username : {}", registeredUser.getUsername());
        return registeredUser;
    }

    private User buildUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> userRoles = roleRepository.findByName("ROLE_USER");
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        return user;
    }

    private boolean isUsernameUsed(String username) {
        try {
            findByUsername(username);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    private Profile buildProfile(Profile profile, User registeredUser) {
        profile.setUser(registeredUser);
        profile.setSex(NOT_SPECIFIED);
        profile.setFamilyStatus(NOT_SPECIFIED);
        profile.setTown(TOWN_DEFAULT);
        profile.setPhone(NOT_SPECIFIED);
        return profile;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);

        if (result == null) {
            log.warn("IN findByUsername - haven't founded user by username: {}", username);
            throw new UserNotFoundException("User haven't founded by username : " + username);
        }

        log.info("IN findByUsername - have founded user by username: {}", username);
        return result;
    }

    /**
     * For updating user role
     * @param user that will update
     * @return user that have been updated
     */
    @Override
    @Transactional
    public User updateRoleGroupManager(User user) {
        findById(user.getId());
        List<Role> roles = user.getRoles();
        roles.add(roleRepository.findByName(GROUP_MANAGER).get(0));
        user.setRoles(roles);
        User updatedUser = userRepository.save(user);
        log.info("IN updateRoleGroupManager - have added role group manager in user with username : {}", user.getUsername());
        return updatedUser;
    }

    /**
     * For finding user by id
     * @param id to find user
     * @return user that have been founded
     */
    @Override
    @Transactional
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            log.warn("IN findById - haven't founded user by id: {}", id);
            throw new UserNotFoundException("User haven't been founded by id : " + id);
        }
        User result = user.get();
        log.info("IN findById - have founded user by id: {}", result.getId());
        return result;
    }
}
