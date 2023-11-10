package com.senla.social.service;

import com.senla.social.entity.Message;
import com.senla.social.entity.Profile;
import com.senla.social.entity.User;

import java.util.Map;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
public interface ProfileService {

    void update(Profile profile);

    Profile findByUser(User user);

    Map<Message, String> findAllMessages(Profile sender, Long receiverId);

    void sendMessage(Profile sender, Long receiverId, String text);

    Profile findById(Long id);
}
