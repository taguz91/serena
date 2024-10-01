package com.taguz91.api_serena.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "registers_students")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RegisterStudent extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private String photo;

    private String emotion;

    private String status;

    @JsonManagedReference(value = "rf_register_student_register")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Register register;

    @JsonManagedReference(value = "rf_register_student_student")
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
