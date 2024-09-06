package com.taguz91.api_serena.api.request;

import com.taguz91.api_serena.models.ClassSummary;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClassSummaryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 819512021687550742L;

    @NotEmpty
    private LocalDateTime summaryText;

    private String avgEmotion;

    private String minEmotion;

    private String maxEmotion;

    private String classroomId;

    public ClassSummary toClassSummary() {
        return new ClassSummary()
                .setId((new NanoCombCreator()).create().toString())
                .setDate(LocalDateTime.now())
                .setAvg_emotion(avgEmotion)
                .setMin_emotion(minEmotion)
                .setMax_emotion(maxEmotion);
    }
}
