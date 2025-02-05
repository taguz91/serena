package com.taguz91.api_serena.repository;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.models.AcademicPeriod;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcademicPeriodRepository extends JpaRepository<AcademicPeriod, String> {

    @Query("SELECT ap.id as value, concat(ap.carrera.name, \" - \", ap.name) as label FROM AcademicPeriod ap WHERE isDeleted = false ORDER BY label")
    List<OptionResponse> findAllOptions();

    @Query("SELECT ap FROM AcademicPeriod ap WHERE ap.carrera.id = :idCarrier AND ap.isActive = true")
    Optional<AcademicPeriod> findActiveByIdCarrier(
            @Param("idCarrier") String idCarrier
    );
}
