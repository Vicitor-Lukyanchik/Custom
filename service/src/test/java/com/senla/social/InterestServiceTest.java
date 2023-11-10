package com.senla.social;

import com.senla.social.config.TestContextConfiguration;
import com.senla.social.entity.Interest;
import com.senla.social.exception.ServiceException;
import com.senla.social.repository.InterestRepository;
import com.senla.social.service.impl.InterestServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {TestContextConfiguration.class})
public class InterestServiceTest {

    @InjectMocks
    private InterestServiceImpl interestService;

    @Mock
    private InterestRepository interestRepository;

    private static BeanValidator validator = new BeanValidator();

    @BeforeAll
    public static void createValidator() {
        validator.setUp();
    }

    @Test
    void create_ShouldThrowException_WhenNameIsEmpty() {
        Interest interest = new Interest(1l, "");

        assertThrows(ValidationException.class, () -> validator.validate(interest));
    }

    @Test
    void create_ShouldThrowException_WhenNameMoreThan50() {
        Interest interest = new Interest(1l, "HelloHelloHelloHelloHelloHelloHelloHelloHelloHelloHello");

        assertThrows(ValidationException.class, () -> validator.validate(interest));
    }

    @Test
    void create_ShouldCreateInterest() {
        Interest interest = new Interest(1l, "Hello apl");

        given(interestRepository.save(isA(Interest.class))).willReturn(interest);

        interestService.create(interest);

        verify(interestRepository, Mockito.times(1)).save(interest);
    }

    @Test
    void findByName_ShouldThrowException_WhenThereIsNotInterest() {
        given(interestRepository.findByName(isA(String.class))).willReturn(null);

        assertThrows(ServiceException.class, () -> interestService.findByName("Apl"));
    }

    @Test
    void findByName_ShouldReturnInterest() {
        Interest interest = new Interest(1l, "Apl");

        given(interestRepository.findByName(isA(String.class))).willReturn(interest);

        interestService.findByName("Apl");

        verify(interestRepository, Mockito.times(1)).findByName("Apl");
    }
}
