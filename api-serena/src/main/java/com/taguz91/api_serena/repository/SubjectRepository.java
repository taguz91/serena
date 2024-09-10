package com.taguz91.api_serena.repository;

import java.util.List;
import java.util.Optional;

import com.taguz91.api_serena.api.response.OptionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.taguz91.api_serena.models.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {

    @Query("SELECT ap.id as value, ap.name as label FROM Subject ap WHERE isDeleted = false ORDER BY label")
    List<OptionResponse> findAllOptions();
}
