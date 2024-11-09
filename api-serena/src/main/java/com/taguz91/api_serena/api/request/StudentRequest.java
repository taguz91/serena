package com.taguz91.api_serena.api.request;

import java.io.Serial;
import java.io.Serializable;

import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.utils.NanoCombCreator;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    @NotEmpty
    @Pattern(regexp = "[^.]{2,200}")
    private String name;

    @NotEmpty
    @Pattern(regexp = "[^.]{2,200}")
    private String lastname;

    @NotEmpty
    private String identification;

    @NotEmpty
    private String gender;

    private String reference;

    public Student toStudent() {
        return new Student()
                .setId((new NanoCombCreator()).create().toString())
                .setName(name)
                .setLastname(lastname)
                .setIdentification(identification)
                .setGender(gender)
                .setReference(reference);
    }
}