package com.taguz91.api_serena.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2884596714048044738L;

    private String name;

    private String email;

    private String password;
}
