package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class TeacherRequest  implements Serializable {
    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    @NotEmpty
    @Pattern(regexp = "[^.]{2,200}")
    private  String name;

    @NotEmpty
    @Email
    private String  email;

    public Teacher toTeacher() {
        return new Teacher()
                .setId((new NanoCombCreator()).create().toString())
                .setName(name)
                .setEmail(email)
                .setPassword("123");
    }
}
