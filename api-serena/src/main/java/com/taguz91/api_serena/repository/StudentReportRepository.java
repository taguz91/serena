package com.taguz91.api_serena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.taguz91.api_serena.models.StudentReport;
import java.util.List;


@Repository
public interface StudentReportRepository extends JpaRepository<StudentReport, String> {
    
    List<StudentReport> findByClassroomId(@Param("classroomId") String classroomId);
    List<StudentReport> findByStudentId(@Param("studentId") String studentId);
}
