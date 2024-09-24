package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.AcademicPeriod;
import com.taguz91.api_serena.models.Carrera;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class AcademicPeriodRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 819512021687550742L;

    @NotEmpty
    private String name;

    private String reference;

    private Boolean isActive;

    private String idCarrera;

    public AcademicPeriod toAcademicPeriod() {
        return new AcademicPeriod()
                .setId((new NanoCombCreator()).create().toString())
                .setName(name)
                .setReference(reference)
                .setIsActive(isActive)
                .setCarrera((new Carrera()).setId(idCarrera));
    }
}
