package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.Friends;
import com.senla.social.entity.Message;
import com.senla.social.entity.Profile;
import com.senla.social.entity.User;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.ProfileRepository;
import com.senla.social.service.FriendsService;
import com.senla.social.service.MessageService;
import com.senla.social.service.impl.ProfileServiceImpl;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class ProfileServiceTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private FriendsService friendsService;

    @Mock
    private MessageService messageService;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void update_ShouldThrowException_WhenSexNotValid() {
        User user = new User(1l, "useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MAL",
                18, "Baranovichi",
                "6344224", "married", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldThrowException_WhenFirstLetterInTownIsLowercase() {
        User user = new User(1l, "useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MALE",
                18, "baranovichi",
                "6344224", "married", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldThrowException_WhenTownLessThan2() {
        User user = new User(1l, "useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MALE",
                18, "B",
                "6344224", "married", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldThrowException_WhenTownMoreThan50() {
        User user = new User(1l, "useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MALE",
                18, "Baranovichiaranovichiaranovichiaranovichiaranovichi",
                "6344224", "married", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldThrowException_WhenPhoneMoreThan15() {
        User user = new User(1l, "useruser", "23451234111111111");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MALE",
                18, "Baranovichi",
                "6344224", "married", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldThrowException_WhenPhoneLessThan7() {
        User user = new User(1l, "useruser", "2345");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MALE",
                18, "Baranovichi",
                "6344224", "married", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldThrowException_WhenFamilyStatusMoreThan50() {
        User user = new User(1l, "useruser", "2345");
        Profile profile = new Profile("Andrey", "Lovchin",
                "gigmail.com", "MALE",
                18, "Baranovichi",
                "6344224", "marriedmarriedmarriedmarriedmarriedmarriedmarriedmarried", user);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void update_ShouldUpdateProfile() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileRepository.save(isA(Profile.class))).willReturn(profile);

        profileService.update(profile);

        verify(profileRepository, Mockito.times(1)).save(profile);
    }

    @Test
    void findByUser_ShouldThrowException_WhenUserNotFound() {
        User user = new User(1l, "useruser", "23451234");

        given(profileRepository.findProfileByUser(isA(User.class))).willReturn(null);

        assertThrows(ServiceException.class, () -> profileService.findByUser(user));
    }

    @Test
    void findByUser_ShouldReturnProfile() {
        User user = new User(1l, "useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileRepository.findProfileByUser(isA(User.class))).willReturn(profile);

        Profile actual = profileService.findByUser(user);

        assertEquals(profile, actual);
    }

    @Test
    void findById_ShouldThrowException_WhenProfileNotFound() {
        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.findById(1l));
    }

    @Test
    void findById_ShouldReturnProfile() {
        Profile profile = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(profile));

        Profile actual = profileService.findById(1l);

        assertEquals(profile, actual);
    }

    @Test
    void findAllMessages_ShouldThrowException_WhenThereIsNotMessages() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(sender, receiver);

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(receiver));
        given(friendsService.findAllBySender(isA(Profile.class))).willReturn(Arrays.asList(friends));
        given(messageService.findAllByFriends(isA(Friends.class))).willReturn(new ArrayList<>());

        assertThrows(ServiceException.class, () -> profileService.findAllMessages(sender, 2l));
    }

    @Test
    void findAllMessages_ShouldThrowException_WhenSenderIsReceiver() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(receiver));

        assertThrows(ServiceException.class, () -> profileService.findAllMessages(sender, 2l));
    }

    @Test
    void findAllMessages_ShouldReturnMessages() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(sender, receiver);

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(receiver));
        given(friendsService.findAllBySender(isA(Profile.class))).willReturn(Arrays.asList(friends));
        given(messageService.findAllByFriends(isA(Friends.class)))
                .willReturn(Arrays.asList(new Message(friends, "Hello", LocalDateTime.now())));

        Map<Message, String> actual = profileService.findAllMessages(sender, 2l);

        assertEquals(1, actual.size());
    }

    @Test
    void sendMessage_ShouldThrowException_WhenSenderIsReceiver() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(receiver));

        assertThrows(ServiceException.class, () -> profileService.sendMessage(sender, 1l, "Hello"));
    }

    @Test
    void sendMessage_ShouldSendMessage() {
        Profile sender = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        Profile receiver = new Profile(2l, "Pavel", "Lovchin", "gig1@gmail.com", 18);
        Friends friends = new Friends(sender, receiver);

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(receiver));
        given(friendsService.findAllBySender(isA(Profile.class))).willReturn(Arrays.asList(friends));
        doNothing().when(messageService).create(isA(Message.class));

        profileService.sendMessage(sender, 2l, "Hello");

        verify(messageService, Mockito.times(1)).create(isA(Message.class));
    }
}
