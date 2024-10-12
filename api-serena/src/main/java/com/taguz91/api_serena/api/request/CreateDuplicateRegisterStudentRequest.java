package com.taguz91.api_serena.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDuplicateRegisterStudentRequest {
    @NotBlank
    private String idRegister;

    @NotBlank
    private String idStudent;

    private String photo;
}
