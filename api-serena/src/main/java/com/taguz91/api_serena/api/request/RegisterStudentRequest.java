package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.RegisterStudent;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.taguz91.api_serena.models.Register;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.utils.NanoCombCreator;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class RegisterStudentRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567890L;

    @NotEmpty
    private String photo;

    @NotEmpty
    private String emotion;

    @NotEmpty
    private String registerId;

    @NotEmpty
    private String studentId;

    public RegisterStudent toRegisterStudent() {
        return new RegisterStudent()
                .setId((new NanoCombCreator()).create().toString())
                .setPhoto(photo)
                .setEmotion(emotion)
                .setRegister(new Register().setId(registerId))
                .setStudent(new Student().setId(studentId));
    }
}