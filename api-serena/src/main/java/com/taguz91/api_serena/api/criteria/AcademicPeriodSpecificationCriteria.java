package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.models.AcademicPeriod;
import org.springframework.data.jpa.domain.Specification;

public class AcademicPeriodSpecificationCriteria extends BaseCriteria<AcademicPeriod> implements Specification<AcademicPeriod> {
    public AcademicPeriodSpecificationCriteria(SearchCriteria criteria) {
        super(criteria);
    }
}
