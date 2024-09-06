package com.taguz91.api_serena.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.taguz91.api_serena.models.ClassSummary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository

public interface ClassSummaryRepository extends JpaRepository<ClassSummary, String>{
     Optional<ClassSummary> findByDate(@Param("date") LocalDateTime date);
}
