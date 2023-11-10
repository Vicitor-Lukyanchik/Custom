package com.senla.social.service.impl;

import com.senla.social.entity.Friends;
import com.senla.social.entity.Profile;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.FriendsRepository;
import com.senla.social.service.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Friends Service
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FriendsServiceImpl implements FriendsService {

    /**
     * For work with friends database
     */
    private final FriendsRepository friendsRepository;

    /**
     * For creating new friends
     * @param friends that will create
     * @return created friends
     */
    @Override
    @Transactional
    public Friends create(Friends friends) {
        Friends createdFriends = friendsRepository.save(friends);
        log.info("In create - friends successfully have been created by id : {}", createdFriends.getId());
        return createdFriends;
    }

    /**
     * For finding friends by sender
     * @param sender to find friends
     * @return friends that have been founded
     */
    @Override
    @Transactional
    public List<Friends> findAllBySender(Profile sender) {
        List<Friends> friends = friendsRepository.findAllBySender(sender);
        log.info("In findAllBySender - friends successfully have been founded, size : {}", friends.size());
        return friends;
    }

    /**
     * For finding friends by id
     * @param id to find friends
     * @return friends that have been founded
     */
    @Override
    @Transactional
    public Friends findById(Long id) {
        Optional<Friends> friends = friendsRepository.findById(id);

        if (friends.isEmpty()) {
            log.warn("IN findById - haven't founded friends by id: {}", id);
            throw new ServiceException("Friends haven't been founded by id : " + id);
        }

        log.info("IN findById - have founded friends by id: {}", id);
        return friends.get();
    }
}
