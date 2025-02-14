package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.BaseCriteria;
import com.taguz91.api_serena.api.criteria.SearchCriteria;
import com.taguz91.api_serena.api.criteria.SubjectSpecificationCriteria;
import com.taguz91.api_serena.models.Subject;

public class SubjectSpecificationBuilder extends GenericSpecificationBuilder<Subject> {
    @Override
    protected BaseCriteria<Subject> instance(SearchCriteria param) {
        return new SubjectSpecificationCriteria(param);
    }
}
