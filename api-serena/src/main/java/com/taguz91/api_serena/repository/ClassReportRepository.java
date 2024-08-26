package com.taguz91.api_serena.repository;
import com.taguz91.api_serena.models.ClassReport;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository

public interface ClassReportRepository extends JpaRepository<ClassReport, Object>{
    
}
