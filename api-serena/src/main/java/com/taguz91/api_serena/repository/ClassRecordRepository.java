package com.taguz91.api_serena.repository;
import com.taguz91.api_serena.models.ClassRecord;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository

public interface ClassRecordRepository extends JpaRepository<ClassRecord, Object>{
    
}
