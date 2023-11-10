package com.senla.social.service.impl;

import com.senla.social.entity.*;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.ProfileRepository;
import com.senla.social.service.FriendsService;
import com.senla.social.service.MessageService;
import com.senla.social.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ProfileServiceImpl implements ProfileService {

    /**
     * For work with profile database
     */
    private final ProfileRepository profileRepository;
    /**
     * For work with friends
     */
    private final FriendsService friendsService;
    /**
     * For work with messages
     */
    private final MessageService messageService;

    /**
     * For updating profile
     * @param profile that will update
     */
    @Override
    @Transactional
    public void update(@Valid Profile profile) {
        Profile updatedProfile = profileRepository.save(profile);
        log.info("In update - profile successfully have been updated by id : {}", updatedProfile.getId());
    }

    /**
     * For finding profile by user
     * @param user to find profile
     * @return profile that have been founded
     */
    @Override
    @Transactional
    public Profile findByUser(User user) {
        Profile profile = profileRepository.findProfileByUser(user);

        if (profile == null) {
            log.warn("IN findByUser - haven't founded profile by user id: {}", user.getId());
            throw new ServiceException("Profile haven't been founded by user id: " + user.getId());
        }

        log.info("IN findByUser - have founded profile by user id: {}", user.getId());
        return profile;
    }

    /**
     * For finding all messages by sender and by receiver
     * @param sender
     * @param receiverId
     * @return all messages between sender and receiver
     */
    @Override
    @Transactional
    public Map<Message, String> findAllMessages(Profile sender, Long receiverId) {
        Profile receiver = findById(receiverId);
        if (receiverId == sender.getId()){
            log.warn("Can't find messages, because can't send yourself");
            throw new ServiceException("Can't find messages, because can't send yourself");
        }
        Map<Message, String> result = sortMessages(findAllMessagesBySenderAndReceiver(sender, receiver),
                findAllMessagesBySenderAndReceiver(receiver, sender));

        if (result.isEmpty()) {
            log.warn("Messages haven't been founded with profile by id : {}", receiverId);
            throw new ServiceException("Messages haven't been founded with profile by id : " + receiverId);
        }

        log.info("In findAllMessages - messages successfully have been founded, size : {}", result.size());
        return result;
    }

    private Map<Message, String> findAllMessagesBySenderAndReceiver(Profile sender, Profile receiver) {
        List<Friends> friends = friendsService.findAllBySender(sender).stream().filter(f -> f.getReceiver().getId()
                .equals(receiver.getId())).collect(Collectors.toList());
        if (friends.isEmpty()) {
            return new LinkedHashMap<>();
        }
        List<Message> messages = messageService.findAllByFriends(friends.get(0));
        return convertMessages(messages, sender);
    }

    private Map<Message, String> convertMessages(List<Message> messages, Profile sender) {
        Map<Message, String> result = new LinkedHashMap<>();
        for (Message message : messages) {
            result.put(message, sender.getFirstname() + " " + sender.getLastname());
        }
        return result;
    }

    private Map<Message, String> sortMessages(Map<Message, String> senderMessages,
                                              Map<Message, String> receiverMessages) {
        Map<Message, String> result = new LinkedHashMap<>();
        result.putAll(senderMessages);
        result.putAll(receiverMessages);
        return result.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.comparing(Message::getDateTime)))
                .collect(Collectors.toMap(Map.Entry<Message, String>::getKey, Map.Entry<Message, String>::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    /**
     * For sending message
     * @param sender who send message
     * @param receiverId who receive message
     * @param text
     */
    @Override
    @Transactional
    public void sendMessage(Profile sender, Long receiverId, String text) {
        Profile receiver = findById(receiverId);

        if(receiverId == sender.getId()){
            log.warn("Can't send messages by yourself");
            throw new ServiceException("Can't send messages by yourself");
        }

        List<Friends> friends = friendsService.findAllBySender(sender).stream()
                .filter(f -> f.getReceiver().getId().equals(receiverId)).collect(Collectors.toList());

        if (friends.isEmpty()) {
            friends.add(friendsService.create(new Friends(sender, receiver)));
        }
        messageService.create(new Message(friends.get(0), text, LocalDateTime.now()));
        log.info("In sendMessage - message successfully have been sent");
    }

    /**
     * For finding profile by id
     * @param id to find profile
     * @return profile that have been founded
     */
    @Override
    @Transactional
    public Profile findById(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);

        if (profile.isEmpty()) {
            log.warn("IN findById - haven't founded profile by id: {}", id);
            throw new ServiceException("Profile haven't been founded by id : " + id);
        }

        log.info("IN findById - have founded profile by id: {}", id);
        return profile.get();
    }
}
