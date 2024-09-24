
package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "academic_periods")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AcademicPeriod extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private String name;

    @Column(nullable = true)
    private String reference;

    private Boolean isActive;

    @JsonManagedReference(value = "rf_academic_period_carrera")
    @ManyToOne(fetch = FetchType.LAZY)
    private Carrera carrera;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "academicPeriod",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Classroom> classrooms;

}
