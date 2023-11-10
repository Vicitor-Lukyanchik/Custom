package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.*;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.PostRepository;
import com.senla.social.service.impl.PostServiceImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void create_ShouldThrowException_WhenTitleIsEmpty() {
        Post post = new Post("", "Hello apl");

        assertThrows(ValidationException.class, () -> validator.validate(post));
    }

    @Test
    void create_ShouldThrowException_WhenTitleMoreThan80() {
        Post post = new Post("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "Hello apl");

        assertThrows(ValidationException.class, () -> validator.validate(post));
    }

    @Test
    void create_ShouldThrowException_WhenTextIsEmpty() {
        Post post = new Post("Hello", "");

        assertThrows(ValidationException.class, () -> validator.validate(post));
    }

    @Test
    void create_ShouldThrowException_WhenTextMoreThan250() {
        Post post = new Post(
                "Hello apl",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        assertThrows(ValidationException.class, () -> validator.validate(post));
    }

    @Test
    void create_ShouldCreatePost() {
        Post post = new Post("Apl", "Hello apl");

        given(postRepository.save(isA(Post.class))).willReturn(post);

        postService.create(post);

        verify(postRepository, Mockito.times(1)).save(post);
    }

    @Test
    void update_ShouldThrowException_WhenPostIsNotFromThisGroup() {
        Group group = new Group(2l, "Epl", new Interest("football"));
        Post post = new Post(1l, group, "Apl", "Hello apl");
        Group group2 = new Group(1l, "Epl", new Interest("football"));
        Post post2 = new Post(1l, group2, "Apl", "Hello apl");

        given(postRepository.findById(isA(Long.class))).willReturn(Optional.of(post2));

        assertThrows(ServiceException.class, () -> postService.update(post));
    }

    @Test
    void update_ShouldUpdatePost() {
        Group group = new Group(1l, "Epl", new Interest("football"));
        Post post = new Post(1l, group, "Apl", "Hello apl");

        given(postRepository.save(isA(Post.class))).willReturn(post);
        given(postRepository.findById(isA(Long.class))).willReturn(Optional.of(post));

        postService.update(post);

        verify(postRepository, Mockito.times(1)).save(post);
    }

    @Test
    void delete_ShouldThrowException_WhenPostIsNotFromThisGroup() {
        Group group = new Group(2l, "Epl", new Interest("football"));
        Group group2 = new Group(1l, "Epl", new Interest("football"));
        Post post2 = new Post(1l, group2, "Apl", "Hello apl");

        given(postRepository.findById(isA(Long.class))).willReturn(Optional.of(post2));

        assertThrows(ServiceException.class, () -> postService.delete(1l, group));
    }

    @Test
    void delete_ShouldDeletePost() {
        Group group = new Group(1l, "Epl", new Interest("football"));
        Post post = new Post(1l, group, "Apl", "Hello apl");

        doNothing().when(postRepository).delete(isA(Post.class));
        given(postRepository.findById(isA(Long.class))).willReturn(Optional.of(post));

        postService.delete(1l, group);

        verify(postRepository, Mockito.times(1)).delete(post);
    }

    @Test
    void findAllByGroup_ShouldThrowException_WhenProfileIsNotInThisGroup() {
        Group group = new Group(1l, "Epl", new Interest("football"));
        Profile profile = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        profile.setGroups(new ArrayList<>());

        assertThrows(ServiceException.class, () -> postService.findAllByGroup(group, profile));
    }

    @Test
    void findAllByGroup_ShouldReturnPosts() {
        List<Post> posts = Arrays.asList(new Post("Apl", "Hello apl"));
        Group group = new Group(1l, "Epl", new Interest("football"));
        Profile profile = new Profile(1l, "Andrey", "Lovchin", "gig@gmail.com", 18);
        profile.setGroups(Arrays.asList(group));

        given(postRepository.findAllByGroup(isA(Group.class))).willReturn(posts);
        List<Post> actual = postService.findAllByGroup(group, profile);

        assertEquals(posts, actual);
    }
}
