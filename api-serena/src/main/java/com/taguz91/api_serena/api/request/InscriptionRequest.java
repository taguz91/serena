package com.taguz91.api_serena.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class InscriptionRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    private List<String> photos;

    private List<String> students;
}
