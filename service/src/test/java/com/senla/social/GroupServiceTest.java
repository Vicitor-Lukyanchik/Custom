package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.Group;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.GroupRepository;
import com.senla.social.service.impl.GroupServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class GroupServiceTest {

    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupRepository groupRepository;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void create_ShouldThrowException_WhenNameIsEmpty() {
        Group group = new Group();
        group.setName("");

        assertThrows(ValidationException.class, () -> validator.validate(group));
    }

    @Test
    void create_ShouldThrowException_WhenNameMoreThan50() {
        Group group = new Group();
        group.setName("HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloq");

        assertThrows(ValidationException.class, () -> validator.validate(group));
    }

    @Test
    void create_ShouldThrowException_WhenGroupExist() {
        Group group = new Group();
        group.setId(1l);

        given(groupRepository.findById(isA(Long.class))).willReturn(Optional.of(group));

        assertThrows(ServiceException.class, () -> groupService.create(group));
    }

    @Test
    void create_ShouldCreateGroup() {
        Group group = new Group();
        group.setId(1l);

        given(groupRepository.save(isA(Group.class))).willReturn(group);
        given(groupRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        groupService.create(group);

        verify(groupRepository, Mockito.times(1)).save(group);
    }

    @Test
    void update_ShouldThrowException_WhenGroupNotExist() {
        Group group = new Group();
        group.setId(1l);

        given(groupRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> groupService.update(group));
    }

    @Test
    void update_ShouldCreateGroup() {
        Group group = new Group();
        group.setId(1l);

        given(groupRepository.findById(isA(Long.class))).willReturn(Optional.of(group));
        given(groupRepository.save(isA(Group.class))).willReturn(group);

        groupService.update(group);

        verify(groupRepository, Mockito.times(1)).save(group);
    }

    @Test
    void findById_ShouldThrowException_WhenGroupNotExist() {
        Group group = new Group();

        given(groupRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> groupService.findById(1l));
    }

    @Test
    void findById_ShouldReturnGroup() {
        Group group = new Group();

        given(groupRepository.findById(isA(Long.class))).willReturn(Optional.of(group));

        Group actual = groupService.findById(1l);

        assertEquals(group, actual);
    }
}
