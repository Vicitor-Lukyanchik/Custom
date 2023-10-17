package com.example.custom.specification;

import com.example.custom.entity.Chop;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ChopSortByParametersSpecification implements Specification<Chop> {

    private Map<String, Boolean> parameters;

    @Override
    public Predicate toPredicate(Root<Chop> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        for (Map.Entry<String, Boolean> parameter : parameters.entrySet()) {
            if (parameter.getValue()) {
                query.orderBy(criteriaBuilder.desc(root.get(parameter.getKey())));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get(parameter.getKey())));
            }
        }
        List<Predicate> predicates = new ArrayList<>();
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
