package com.senla.social.service.impl;

import com.senla.social.entity.Friends;
import com.senla.social.entity.Message;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.MessageRepository;
import com.senla.social.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {

    /**
     * For work with message database
     */
    private final MessageRepository messageRepository;

    /**
     * For creating new message
     * @param message that will create
     */
    @Override
    @Transactional
    public void create(@Valid Message message) {
        Message createdMessage = messageRepository.save(message);
        log.info("In create - message successfully have been created by id : {}", createdMessage.getId());
    }

    /**
     * For updating message
     * @param message that will update
     */
    @Override
    @Transactional
    public void update(@Valid Message message) {
        if(messageRepository.findById(message.getId()).isEmpty()){
            log.warn("In update - message haven't been updated by id : {}", message.getId());
            throw new ServiceException("Message with id " + message.getId() + " doesn't exist");
        }
        Message updatedMessage = messageRepository.save(message);
        log.info("In update - message successfully have been updated by id : {}", updatedMessage.getId());
    }

    /**
     * For finding all messages by friends
     * @param friends to find messages
     * @return messages that have been founded
     */
    @Override
    @Transactional
    public List<Message> findAllByFriends(Friends friends) {
        List<Message> messages = messageRepository.findAllByFriends(friends);
        log.info("In findAll - messages successfully have been founded, size : {}", messages.size());
        return messages;
    }
}
