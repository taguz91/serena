package com.taguz91.api_serena.models;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
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
@Table(name = "teachers")
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StudentRecord extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4115808525376597079L;

    @Id
    private String id;

    private String reference;

    private String identification;

    private String name;

    private String gender;

    @Column(nullable = true)
    private String photo;

}