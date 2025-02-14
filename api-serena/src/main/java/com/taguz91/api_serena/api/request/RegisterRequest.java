package com.taguz91.api_serena.api.request;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @NotEmpty
    private String status;

    @NotNull
    private String idClassroom;

    private String topic;

    public RegisterRequest() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE_TIME;
        this.topic = "Clase " + date.format(dateFormat);
    }

    public Register toRegister() {
        return new Register()
                .setId(new NanoCombCreator().create().toString())
                .setDate(LocalDateTime.now())
                .setStatus(status)
                .setTopic(topic != null ? topic : "Clase")
                .setClassroom(
                        (new Classroom()).setId(idClassroom)
                );
    }
}
