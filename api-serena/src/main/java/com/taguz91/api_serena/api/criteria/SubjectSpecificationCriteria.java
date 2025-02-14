package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.models.Subject;
import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecificationCriteria extends BaseCriteria<Subject> implements Specification<Subject> {
    public SubjectSpecificationCriteria(SearchCriteria criteria) {
        super(criteria);
    }
}
