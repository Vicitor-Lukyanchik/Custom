package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.*;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.MessageRepository;
import com.senla.social.service.MessageService;
import com.senla.social.service.impl.MessageServiceImpl;
import com.senla.social.validator.BeanValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class MessageServiceTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void create_ShouldThrowException_WhenDateTimeIsEmpty() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(1l, sender, receiver);
        Message message = new Message(friends, "Hello");

        assertThrows(ValidationException.class, () -> validator.validate(message));
    }

    @Test
    void create_ShouldThrowException_WhenTextIsEmpty() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(1l, sender, receiver);
        Message message = new Message(friends, "", LocalDateTime.now());

        assertThrows(ValidationException.class, () -> validator.validate(message));
    }

    @Test
    void create_ShouldThrowException_WhenTextMoreThan255() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(1l, sender, receiver);
        Message message = new Message(friends,
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                LocalDateTime.now());

        assertThrows(ValidationException.class, () -> validator.validate(message));
    }

    @Test
    void create_ShouldCreateMessage() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(1l, sender, receiver);
        Message message = new Message(friends, "Hello");

        given(messageRepository.save(isA(Message.class))).willReturn(message);

        messageService.create(message);

        verify(messageRepository, Mockito.times(1)).save(message);
    }

    @Test
    void update_ShouldThrowException_WhenTextMoreThan255() {
        Message message = new Message(1l, new Friends(), "Hello", LocalDateTime.now());

        given(messageRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> messageService.update(message));
    }

    @Test
    void update_ShouldUpdateMessage() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(1l, sender, receiver);
        Message message = new Message(1l, friends, "Hello", LocalDateTime.now());

        given(messageRepository.save(isA(Message.class))).willReturn(message);
        given(messageRepository.findById(isA(Long.class))).willReturn(Optional.of(message));

        messageService.update(message);

        verify(messageRepository, Mockito.times(1)).save(message);
    }

    @Test
    void findAllByGroup_ShouldReturnPosts() {
        Friends friends = new Friends();
        List<Message> messages = Arrays.asList(new Message(1l, friends, "Hello", LocalDateTime.now()));

        given(messageRepository.findAllByFriends(isA(Friends.class))).willReturn(messages);
        List<Message> actual = messageService.findAllByFriends(friends);

        assertEquals(messages, actual);
    }
}
