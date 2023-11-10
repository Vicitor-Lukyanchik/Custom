package com.senla.social.service.impl;

import com.senla.social.entity.Group;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.GroupRepository;
import com.senla.social.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Group Service
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class GroupServiceImpl implements GroupService {

    /**
     * For work with group database
     */
    private final GroupRepository groupRepository;

    /**
     * For creating new group
     * @param group that will create
     */
    @Override
    @Transactional
    public void create(@Valid Group group) {
        if (isExist(group)) {
            log.info("Group with id : {} is exist", group.getId());
            throw new ServiceException("Group with id : " + group.getId() + " is exist");
        }
        groupRepository.save(group);
        log.info("In create - group successfully have been created by id : {}", group.getId());
    }

    @Transactional
    public boolean isExist(Group group) {
            return !groupRepository.findById(group.getId()).isEmpty();
    }

    /**
     * For updating new group
     * @param group that will update
     */
    @Override
    @Transactional
    public void update(@Valid Group group) {
        findById(group.getId());
        Group updatedGroup = groupRepository.save(group);
        log.info("In update - group successfully have been updated by id : {}", updatedGroup.getId());
    }

    /**
     * For finding group by id
     * @param id to find group
     * @return group that have been founded
     */
    @Override
    @Transactional
    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);

        if (group.isEmpty()) {
            log.warn("IN findById - haven't founded group by id: {}", id);
            throw new ServiceException("Group haven't been founded by id : " + id);
        }

        log.info("IN findById - have founded group by id: {}", id);
        return group.get();
    }
}
