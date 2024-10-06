package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, String> {
    @Query("SELECT i FROM Inscription i WHERE i.classroom.id = :idClassroom")
    Optional<Inscription> findByIdClassroom(@Param("idClassroom") String idClassroom);



}
