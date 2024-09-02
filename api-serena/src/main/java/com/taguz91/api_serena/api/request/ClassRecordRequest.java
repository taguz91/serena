package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.ClassRecord;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class ClassRecordRequest implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1234567890L;

    @NotEmpty
    private String studentReference;

    @NotEmpty
    private String teacherReference;

    public ClassRecord toClassRecord() {
        return new ClassRecord()
                .setId((new NanoCombCreator()).create().toString())
                .setStudentReference(studentReference)
                .setTeacherReference(teacherReference);
    }
}
