package com.taguz91.api_serena.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckStudentResponse {

    private List<EmotionStudent> emotions;
}
