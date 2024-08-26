package com.taguz91.api_serena.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taguz91.api_serena.models.StudentRecord;



@Repository
public interface StudentRecordRepository extends JpaRepository<StudentRecord ,String>{

}
