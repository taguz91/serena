package com.taguz91.api_serena.api.request;

import java.io.Serial;
import java.io.Serializable;

import com.taguz91.api_serena.models.Subject;
import com.taguz91.api_serena.utils.NanoCombCreator;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubjectRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5643849213470286453L;

    @NotEmpty
    @Pattern(regexp = "[^.]{2,200}")
    private String name;


    public Subject toSubject() {
        return new Subject()
                .setId((new NanoCombCreator()).create().toString())
                .setName(name);
    }
}
