package com.senla.social.service;

import com.senla.social.entity.Group;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface GroupService {

    void create(Group group);

    void update(Group group);

    Group findById(Long id);
}
