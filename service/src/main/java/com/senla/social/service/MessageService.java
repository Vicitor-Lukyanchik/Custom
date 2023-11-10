package com.senla.social.service;

import com.senla.social.entity.Friends;
import com.senla.social.entity.Message;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface MessageService {

    void create(Message message);

    void update(Message message);

    List<Message> findAllByFriends(Friends friends);
}
