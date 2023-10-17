package com.example.custom.specification;

import com.example.custom.entity.Chop;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChopSpecifications {

    private ChopSpecifications(){}

    public static Specification<Chop> findByNameWithSortByParameters(String name, Map<String, Boolean> parameters) {
        return new InterestFindByNameAndSortByParametersSpecification(name, parameters);
    }

    public static Specification<Chop> findWithSortByParameters(Map<String, Boolean> parameters) {
        return new ChopSortByParametersSpecification(parameters);
    }

}
