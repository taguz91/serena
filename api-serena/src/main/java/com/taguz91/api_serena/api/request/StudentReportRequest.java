package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.StudentReport;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.taguz91.api_serena.models.Classroom;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.utils.NanoCombCreator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentReportRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567890L;

    private LocalDateTime date;

    @NotEmpty
    private String startEmotion;

    @NotEmpty
    private String endEmotion;

    @NotEmpty
    private String classroomId;

    @NotEmpty
    private String studentId;

    public StudentReport toStudentReport() {
        return new StudentReport()
                .setId((new NanoCombCreator()).create().toString())
                .setDate(date)
                .setStartEmotion(startEmotion)
                .setEndEmotion(endEmotion)
                .setClassroom(new Classroom().setId(classroomId))
                .setStudent(new Student().setId(studentId));
    }
}

