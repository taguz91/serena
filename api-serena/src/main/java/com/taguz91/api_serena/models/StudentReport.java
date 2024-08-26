package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "students_reports")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentReport extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private LocalDateTime date;

    private String startEmotion;

    private String endEmotion;

    @JsonManagedReference(value = "rf_student_report")
    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    @JsonManagedReference(value = "rf_student")
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
