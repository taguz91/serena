package com.taguz91.api_serena.repository;
import com.taguz91.api_serena.models.ClassRecord;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository

public interface ClassRecordRepository extends JpaRepository<ClassRecord, Object>{

    List<ClassRecord> findByStudentReference(@Param("studentReference") String studentReference);


    List<ClassRecord> findByTeacherReference(@Param("teacherReference") String teacherReference);


    
}
