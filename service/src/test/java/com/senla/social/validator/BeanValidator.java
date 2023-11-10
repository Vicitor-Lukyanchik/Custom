package com.senla.social.validator;

import javax.validation.*;
import java.util.Optional;

public class BeanValidator {

    private static Validator validator;

    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validate(Object bean) throws AssertionError {
        Optional<ConstraintViolation<Object>> violation = validator.validate(bean).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
