package com.senla.social.converter;

import com.senla.social.dto.ProfileDto;
import com.senla.social.entity.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Component
public class ProfileConverter {

    public Profile convertToEntity(ProfileDto profileDto, Profile profile) {
        return new Profile(profile.getId(), profileDto.getFirstname(), profileDto.getLastname(), profileDto.getEmail(),
                profileDto.getSex(), profileDto.getAge(), profileDto.getTown(), profileDto.getPhone(),
                profileDto.getFamilyStatus(), profile.getUser(), profile.getGroups());
    }
}
