package com.taguz91.api_serena.api.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse {

    private List<?> data;
    private PageMetadata meta;

    public PageResponse(Page<?> page) {
        this.data = page.getContent();
        this.meta = new PageMetadata(
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize()
        );
    }
}
