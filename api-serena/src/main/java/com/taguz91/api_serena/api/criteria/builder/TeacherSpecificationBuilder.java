package com.taguz91.api_serena.api.criteria.builder;

import com.taguz91.api_serena.api.criteria.SearchCriteria;
import com.taguz91.api_serena.api.criteria.TeacherSpecificationCriteria;
import com.taguz91.api_serena.models.Teacher;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeacherSpecificationBuilder {

    private final List<SearchCriteria> params;

    public TeacherSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final TeacherSpecificationBuilder with(
            String key,
            String operation,
            String value
    ) {
        if (operation.equals(":")) {
            return with(key, value);
        }

        return this;
    }

    public final TeacherSpecificationBuilder with(
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

    public Specification<Teacher> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Teacher> result = new TeacherSpecificationCriteria(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).or(new TeacherSpecificationCriteria(params.get(i)));
        }

        return result;
    }
}
