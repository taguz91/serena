package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.ClassReport;
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
public class ClassReportRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567890L;

    @NotEmpty
    private String startEmotion;

    @NotEmpty
    private String endEmotion;

    @NotEmpty
    private String registerId;

    @NotEmpty
    private String studentId;

    public ClassReport toClassReport() {
        return new ClassReport()
                .setId((new NanoCombCreator()).create().toString())
                .setStartEmotion(startEmotion)
                .setEndEmotion(endEmotion)
                .setRegister(new Register().setId(registerId))
                .setStudent(new Student().setId(studentId));
    }
}