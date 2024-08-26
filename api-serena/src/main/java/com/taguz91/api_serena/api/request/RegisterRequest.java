package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "[^.]{8,30}")
    private String password;

    public Teacher toTeacher()
    {
        return new Teacher()
                .setId((new NanoCombCreator()).create().toString())
                .setIsActive(false)
                .setName(email.split("@")[0])
                .setEmail(email)
                .setPassword(password);
    }
}
