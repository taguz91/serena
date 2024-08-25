package com.taguz91.api_serena.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "studentreports")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentReport extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private Student student;
    private AcademicPeriod academicPeriod;
    private List<Subject> subjects;
}
