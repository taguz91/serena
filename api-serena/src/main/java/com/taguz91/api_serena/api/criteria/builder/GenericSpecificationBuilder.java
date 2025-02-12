package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.BaseCriteria;
import com.taguz91.api_serena.api.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericSpecificationBuilder<T> {

    private final List<SearchCriteria> params;

    public GenericSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final GenericSpecificationBuilder<T> with(
            String key,
            String operation,
            String value
    ) {
        if (operation.equals(":")) {
            return with(key, value);
        }

        return this;
    }

    public final GenericSpecificationBuilder<T> with(
            String key,
            String value
    ) {
        params.add(
                new SearchCriteria(
                        key,
                        ":",
                        value
                )
        );

        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<T> result = this.instance(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).or(this.instance(params.get(i)));
        }

        return result;
    }

    protected abstract BaseCriteria<T> instance(SearchCriteria param);
}
