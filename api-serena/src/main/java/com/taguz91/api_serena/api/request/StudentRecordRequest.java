package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.StudentRecord;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class StudentRecordRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567890L;

    @NotEmpty
    private String reference;

    @NotEmpty
    private String identification;

    @NotEmpty
    private String name;

    @NotEmpty
    private String gender;

    private String photo;

    public StudentRecord toStudentRecord() {
        return new StudentRecord()
                .setId((new NanoCombCreator()).create().toString())
                .setReference(reference)
                .setIdentification(identification)
                .setName(name)
                .setGender(gender)
                .setPhoto(photo);
    }
}
