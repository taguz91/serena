package com.taguz91.api_serena.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindStudentResponse {

    private String message;

    private String idStudent;
}
