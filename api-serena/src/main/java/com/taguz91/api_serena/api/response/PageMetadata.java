package com.taguz91.api_serena.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageMetadata {

    private int current;
    private long items;
    private int pages;
    private int perPage;
}
