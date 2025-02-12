package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.AcademicPeriodSpecificationCriteria;
import com.taguz91.api_serena.api.criteria.BaseCriteria;
import com.taguz91.api_serena.api.criteria.SearchCriteria;
import com.taguz91.api_serena.models.AcademicPeriod;

public class AcademicPeriodSpecificationBuilder extends GenericSpecificationBuilder<AcademicPeriod> {

    @Override
    protected BaseCriteria<AcademicPeriod> instance(SearchCriteria param) {
        return new AcademicPeriodSpecificationCriteria(param);
    }
}
