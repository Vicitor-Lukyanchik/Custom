package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.Friends;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.FriendsRepository;
import com.senla.social.service.impl.FriendsServiceImpl;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class FriendsServiceTest {

    @InjectMocks
    private FriendsServiceImpl friendsService;

    @Mock
    private FriendsRepository friendsRepository;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void create_ShouldCreateFriends() {
        Friends friends = new Friends();

        given(friendsRepository.save(isA(Friends.class))).willReturn(friends);

        friendsService.create(friends);

        verify(friendsRepository, Mockito.times(1)).save(friends);
    }

    @Test
    void findById_ShouldThrowException_WhenFriendsNotExist() {
        Friends friends = new Friends();

        given(friendsRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> friendsService.findById(1l));
    }

    @Test
    void findById_ShouldReturnFriends() {
        Friends friends = new Friends();

        given(friendsRepository.findById(isA(Long.class))).willReturn(Optional.of(friends));

        Friends actual = friendsService.findById(1l);

        assertEquals(friends, actual);
    }
}
