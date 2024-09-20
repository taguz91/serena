package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "students")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private String identification;

    private String name;

    private String gender;
    
    private Carrera carrera;


    @Column(nullable = true)
    private String reference;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "student",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<RegisterStudent> registers;
}
