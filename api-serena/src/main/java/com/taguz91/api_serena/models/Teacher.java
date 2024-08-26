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
@Table(name = "teachers")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    @Column(nullable = true)
    private String reference;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = true, length = 255)
    private String token;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<Classroom> classrooms;
}
