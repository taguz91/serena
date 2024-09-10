package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.Classroom;
import com.taguz91.api_serena.models.AcademicPeriod;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.models.Subject;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class ClassroomRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 819512021687550742L;

    @NotBlank
    private String idAcademicPeriod;

    @NotBlank
    private String idTeacher;

    @NotBlank
    private String idSubject;

    public Classroom toClassroom() {
        return new Classroom()
                .setId((new NanoCombCreator()).create().toString())
                .setAcademicPeriod((new AcademicPeriod()).setId(idAcademicPeriod))
                .setTeacher((new Teacher()).setId(idTeacher))
                .setSubject((new Subject()).setId(idSubject));
    }
}
