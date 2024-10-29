package com.taguz91.api_serena.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmotionStudent {

    private String type;
    private Double confidence;
}
