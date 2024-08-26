package com.taguz91.api_serena.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.taguz91.api_serena.models.ClassSummary;
import org.springframework.stereotype.Repository;


@Repository

public interface ClassSummaryRepository extends JpaRepository<ClassSummary, Object>{
    
}
