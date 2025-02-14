package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.models.Carrera;
import org.springframework.data.jpa.domain.Specification;

public class CarreraSpecificationCriteria extends BaseCriteria<Carrera> implements Specification<Carrera> {
    public CarreraSpecificationCriteria(SearchCriteria criteria) {
        super(criteria);
    }
}
