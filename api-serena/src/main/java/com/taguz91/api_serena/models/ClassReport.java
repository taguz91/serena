package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "class_reports")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassReport extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private String startEmotion;

    private String endEmotion;

    @JsonManagedReference(value = "rf_class_report_register")
    @ManyToOne(fetch = FetchType.LAZY)
    private Register register;

    @JsonManagedReference(value = "rf_class_report_student")
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
}
