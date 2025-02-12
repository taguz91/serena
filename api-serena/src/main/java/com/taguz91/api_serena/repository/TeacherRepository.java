package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>, JpaSpecificationExecutor<Teacher> {

    Optional<Teacher> findByEmail(@Param("email") String email);

    @Query("SELECT t.id as value, t.name as label FROM Teacher t WHERE isDeleted = false ORDER BY label")
    List<OptionResponse> findAllOptions();
}
