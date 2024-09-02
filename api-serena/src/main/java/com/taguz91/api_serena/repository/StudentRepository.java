package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByIdentification(@Param("identification") String identification);
}