package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.models.Teacher;
import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecificationCriteria extends BaseCriteria<Teacher> implements Specification<Teacher> {

    public TeacherSpecificationCriteria(SearchCriteria criteria) {
        super(criteria);
    }
}
