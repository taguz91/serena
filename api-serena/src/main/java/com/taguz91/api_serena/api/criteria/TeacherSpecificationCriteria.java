package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.models.Teacher;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class TeacherSpecificationCriteria implements Specification<Teacher> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(
            Root<Teacher> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder
    ) {
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {

                return criteriaBuilder.like(
                        criteriaBuilder.lower(
                                root.<String>get(criteria.getKey())
                        ),
                        "%" + criteria.getValue().toString().toLowerCase() + "%"
                );
            } else {
                return criteriaBuilder.equal(
                        root.get(criteria.getKey()),
                        criteria.getValue()
                );
            }
        }

        return null;
    }
}
