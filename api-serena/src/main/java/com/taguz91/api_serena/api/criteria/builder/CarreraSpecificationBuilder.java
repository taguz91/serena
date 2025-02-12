package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.BaseCriteria;
import com.taguz91.api_serena.api.criteria.CarreraSpecificationCriteria;
import com.taguz91.api_serena.api.criteria.SearchCriteria;
import com.taguz91.api_serena.models.Carrera;

public class CarreraSpecificationBuilder extends  GenericSpecificationBuilder<Carrera> {

    @Override
    protected BaseCriteria<Carrera> instance(SearchCriteria param) {
        return new CarreraSpecificationCriteria(param);
    }
}
