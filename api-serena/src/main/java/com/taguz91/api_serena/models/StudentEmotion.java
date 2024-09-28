package com.taguz91.api_serena.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_emotion")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentEmotion implements Serializable {

    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private String emotion;

    private Double percentage;

    @JsonManagedReference(value = "rf_student_emotion_register_student")
    @ManyToOne
    @JsonIgnore
    private RegisterStudent registerStudent;
}
