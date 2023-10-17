package com.example.custom.specification;

import com.example.custom.entity.Chop;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class InterestFindByNameAndSortByParametersSpecification implements Specification<Chop> {

    private String number;
    private Map<String, Boolean> parameters;

    public InterestFindByNameAndSortByParametersSpecification(String number, Map<String, Boolean> parameters) {
        this.number = number;
        this.parameters = parameters;
    }

    @Override
    public Predicate toPredicate(Root<Chop> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        for (Map.Entry<String, Boolean> parameter : parameters.entrySet()) {
            if (parameter.getValue()) {
                query.orderBy(criteriaBuilder.desc(root.get(parameter.getKey())));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get(parameter.getKey())));
            }
        }
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("number")), "%" + number.toLowerCase() + "%");
    }
}
