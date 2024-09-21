package com.taguz91.api_serena.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.models.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, String> {

    @Query("SELECT c.id as value, c.name as label FROM Carrera c WHERE isDeleted = false ORDER BY label")
    List<OptionResponse> findAllOptions();
}
