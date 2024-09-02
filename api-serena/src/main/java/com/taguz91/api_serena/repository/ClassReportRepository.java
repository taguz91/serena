package com.taguz91.api_serena.repository;
import com.taguz91.api_serena.models.ClassReport;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


@Repository

public interface ClassReportRepository extends JpaRepository<ClassReport, Object>{
    List<ClassReport> findByRegisterId(@Param("registerId") String registerId);

    List<ClassReport> findByStudentId(@Param("studentId") String studentId);

}
