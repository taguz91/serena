package com.taguz91.api_serena.repository;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.models.AcademicPeriod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, String> {

    @Query("SELECT ap.id as value, ap.name as label FROM AcademicPeriod ap WHERE isDeleted = false ORDER BY label")
    List<OptionResponse> findAllOptions();
}
