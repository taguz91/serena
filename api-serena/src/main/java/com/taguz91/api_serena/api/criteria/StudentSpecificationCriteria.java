package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.models.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecificationCriteria extends BaseCriteria<Student> implements Specification<Student> {
    public StudentSpecificationCriteria(SearchCriteria criteria) {
        super(criteria);
    }
}
