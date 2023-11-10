package com.senla.social.authentificate;

import com.senla.social.entity.Profile;
import com.senla.social.entity.User;
import com.senla.social.repository.ProfileRepository;
import com.senla.social.service.ProfileService;
import com.senla.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * For getting profile that authorized
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Component
@RequiredArgsConstructor
public class ProfileAuthenticatorImpl implements ProfileAuthenticator {

    private final ProfileService profileService;
    private final UserService userService;

    public Profile getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        return profileService.findByUser(user);
    }
}
