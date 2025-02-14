package com.taguz91.api_serena.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEmailRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    @NotEmpty
    @Email
    private String email;
}
