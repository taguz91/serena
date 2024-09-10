package com.taguz91.api_serena.models;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "registers")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Register extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private LocalDateTime date;

    private String status;

    @JsonManagedReference(value = "rf_register_classroom")
    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "register",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<RegisterStudent> students;
}


