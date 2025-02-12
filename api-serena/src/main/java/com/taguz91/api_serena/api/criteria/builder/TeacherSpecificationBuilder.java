package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.BaseCriteria;
import com.taguz91.api_serena.api.criteria.SearchCriteria;
import com.taguz91.api_serena.api.criteria.TeacherSpecificationCriteria;
import com.taguz91.api_serena.models.Teacher;

public class TeacherSpecificationBuilder extends  GenericSpecificationBuilder<Teacher> {

    @Override
    protected BaseCriteria<Teacher> instance(SearchCriteria param) {
        return new TeacherSpecificationCriteria(param);
    }
}
