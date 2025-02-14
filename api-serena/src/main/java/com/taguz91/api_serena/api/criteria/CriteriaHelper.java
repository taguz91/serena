package com.taguz91.api_serena.api.criteria;

import com.taguz91.api_serena.api.criteria.builder.GenericSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaHelper<T> {
    private final GenericSpecificationBuilder<T> builder;

    public CriteriaHelper(
            GenericSpecificationBuilder<T> builder
    ) {
        this.builder = builder;
    }

    public Specification<T> build(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        return builder.build();
    }
}
