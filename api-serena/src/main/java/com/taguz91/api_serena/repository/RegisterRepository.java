package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.models.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<Register, String> {
    @Query("SELECT r FROM Register r WHERE r.classroom.id = :idClassroom AND r.status = 'open'")
    Optional<Register> findByIdClassroom(@Param("idClassroom") String idClassroom);
}