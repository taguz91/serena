package com.taguz91.api_serena.api.request;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.taguz91.api_serena.models.Classroom;
import com.taguz91.api_serena.models.Register;
import com.taguz91.api_serena.utils.NanoCombCreator;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    @NotNull
    private LocalDateTime date;

    @NotEmpty
    private String status;

    @NotNull
    private Classroom classroom;

    public Register toRegister() {
        return new Register()
                .setId(new NanoCombCreator().create().toString())
                .setDate(date)
                .setStatus(status)
                .setClassroom(classroom);
    }
}
