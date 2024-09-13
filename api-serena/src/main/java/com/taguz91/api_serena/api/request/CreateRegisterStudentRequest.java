package com.taguz91.api_serena.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRegisterStudentRequest {

    @NotBlank
    private String photo;

    @NotBlank
    private String idRegister;
}
