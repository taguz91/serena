package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {

    // Método para buscar un aula por el nombre del periodo académico
    Optional<Classroom> findByAcademicPeriod_Name(@Param("academicPeriodName") String academicPeriodName);
}
