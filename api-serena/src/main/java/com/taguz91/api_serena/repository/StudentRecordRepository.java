package com.taguz91.api_serena.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.taguz91.api_serena.models.StudentRecord;
import java.util.Optional;



@Repository
public interface StudentRecordRepository extends JpaRepository<StudentRecord ,String>{
    Optional<StudentRecord> findByIdentification(@Param("identification") String identification);

    Optional<StudentRecord> findByName(@Param("name") String name);
}
