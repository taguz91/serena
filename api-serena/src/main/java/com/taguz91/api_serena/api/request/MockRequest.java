package com.taguz91.api_serena.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MockRequest {

    @NotNull
    private String idCarrier;

    @NotNull
    private String date;
}
