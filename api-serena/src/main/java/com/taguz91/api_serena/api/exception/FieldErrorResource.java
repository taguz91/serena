package com.taguz91.api_serena.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Data
public class FieldErrorResource {
    private String resource;
    private String field;
    private String code;
    private String message;
}
