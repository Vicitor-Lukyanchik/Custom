package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.Profile;
import com.senla.social.entity.Role;
import com.senla.social.entity.Status;
import com.senla.social.entity.User;
import com.senla.social.exception.UserNotFoundException;
import com.senla.social.repository.ProfileRepository;
import com.senla.social.repository.RoleRepository;
import com.senla.social.repository.UserRepository;
import com.senla.social.service.impl.UserServiceImpl;
import com.senla.social.validator.BeanValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.*;

import java.util.ArrayList;
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
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void register_ShouldThrowException_WhenUsernameIsEmpty() {
        User user = new User("", "23451234");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameLessThan6() {
        User user = new User("user", "23451234");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameMoreThan30() {
        User user = new User("abcdefgabcdefgabcdefgabcdefgabcde", "23451234");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void register_ShouldThrowException_WhenPasswordIsEmpty() {
        User user = new User("useruser", "");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    void register_ShouldThrowException_WhenFirstnameMoreThan50() {
        Profile profile = new Profile("Andreyndreyndreyndreyndreyndreyndreyndreyndreyndreyndrey",
                "Lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenFirstnameLessThan2() {
        Profile profile = new Profile("A", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenFirstnameIsEmpty() {
        Profile profile = new Profile("", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenFirstLetterInFirstnameLowercase() {
        Profile profile = new Profile("andrey", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenLastnameMoreThan50() {
        Profile profile = new Profile("Andrey",
                "Lovchinovchinovchinovchinovchinovchinovchinovchinovchinovchinovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenLastnameLessThan2() {
        Profile profile = new Profile("Andrey", "d", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenLastnameIsEmpty() {
        Profile profile = new Profile("Andrey", "", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenFirstLetterInLastnameLowercase() {
        Profile profile = new Profile("Andrey", "lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenAgeLessThan6() {
        Profile profile = new Profile("Andrey", "lovchin", "gig@gmail.com", 5);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenAgeMoreThan120() {
        Profile profile = new Profile("Andrey", "lovchin", "gig@gmail.com", 121);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldThrowException_WhenMailNotValid() {
        Profile profile = new Profile("Andrey", "lovchin", "gigmail.com", 11);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    void register_ShouldRegisterUser() {
        User user = new User("useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileRepository.save(isA(Profile.class))).willReturn(profile);
        given(userRepository.save(isA(User.class))).willReturn(user);

        userService.register(user, profile);

        verify(userRepository, Mockito.times(1)).save(user);
        verify(profileRepository, Mockito.times(1)).save(profile);
    }

    @Test
    void findByUsername_ShouldThrowException_WhenUserNotFound() {
        given(userRepository.findByUsername(isA(String.class))).willReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("useruser"));
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        User user = new User("useruser", "23451234");

        given(userRepository.findByUsername(isA(String.class))).willReturn(user);
        User actual = userService.findByUsername("useruser");

        assertEquals(user, actual);
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        given(userRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1l));
    }

    @Test
    void findById_ShouldReturnUser() {
        Optional<User> user = Optional.of(new User("useruser", "23451234"));

        given(userRepository.findById(isA(Long.class))).willReturn(user);
        User actual = userService.findById(1l);

        assertEquals(user.get(), actual);
    }

    @Test
    void updateRoleGroupManager_ShouldThrowException_WhenUserNotFound() {
        User user = new User(1l, "useruser", "23451234");
        given(userRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateRoleGroupManager(user));
    }

    @Test
    void updateRoleGroupManager_ShouldUpdateUserRole() {
        User user = new User(1l,"useruser", "23451234");
        user.setRoles(new ArrayList<>());
        List<Role> groupRole = Arrays.asList(new Role("ROLE_GROUP_MANAGER", Status.ACTIVE));

        given(roleRepository.findByName(isA(String.class))).willReturn(groupRole);
        given(userRepository.findById(isA(Long.class))).willReturn(Optional.of(user));
        given(userRepository.save(isA(User.class))).willReturn(user);

        User actual = userService.updateRoleGroupManager(user);
        user.setRoles(groupRole);

        assertEquals(user, actual);
    }
}
