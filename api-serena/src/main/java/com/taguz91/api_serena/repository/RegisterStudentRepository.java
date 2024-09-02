package com.taguz91.api_serena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Student;
import java.util.Optional;

@Repository

public interface RegisterStudentRepository extends JpaRepository<RegisterStudent, Object>{
    
     Optional<Student> findByEmail(@Param("email") String email);

    Optional<Student> findByStudentId(@Param("studentId") String studentId);
}
