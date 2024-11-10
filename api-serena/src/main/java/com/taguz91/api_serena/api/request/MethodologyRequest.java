package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.Methodology;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodologyRequest {

    @NotEmpty
    @Pattern(regexp = "[^.]{2,200}")
    private String name;

    @NotEmpty
    private String summary;

    @NotEmpty
    private List<String> emotions;

    public Methodology toMethodology()
    {
        return new Methodology()
                .setId((new NanoCombCreator()).create().toString())
                .setName(name)
                .setSummary(summary);
    }
}
