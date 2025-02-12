package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.BaseCriteria;
import com.taguz91.api_serena.api.criteria.SearchCriteria;
import com.taguz91.api_serena.api.criteria.StudentSpecificationCriteria;
import com.taguz91.api_serena.models.Student;

public class StudentSpecificationBuilder extends GenericSpecificationBuilder<Student> {
    @Override
    protected BaseCriteria<Student> instance(SearchCriteria param) {
        return new StudentSpecificationCriteria(param);
    }
}
