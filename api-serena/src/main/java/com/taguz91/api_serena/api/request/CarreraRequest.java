package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.Carrera;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CarreraRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 819512021687550742L;

    @NotEmpty
    private String name;

    private String description;

    // Método que convierte el request en una entidad Carrera
    public Carrera toCarrera() {
        return new Carrera()
                .setId((new NanoCombCreator()).create().toString())
                .setName(name)
                .setDescription(description);
    }
}
