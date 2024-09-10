package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "classrooms")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Classroom extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    @JsonManagedReference(value = "rf_classroom_academic_period")
    @ManyToOne(fetch = FetchType.LAZY)
    private AcademicPeriod academicPeriod;

    @JsonManagedReference(value = "rf_classroom_teacher")
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @JsonManagedReference(value = "rf_classroom_subject")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "classroom",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Register> registers;
}
