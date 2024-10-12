package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.models.Register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<Register, String> {
    @Query("SELECT r FROM Register r WHERE r.classroom.id = :idClassroom AND r.status = 'open'")
    Optional<Register> findByIdClassroom(@Param("idClassroom") String idClassroom);

    @Query("SELECT r FROM Register r WHERE r.classroom.id = :idClassroom AND r.status = :status")
    Optional<Register> findByIdClassroomAndStatus(@Param("idClassroom") String idClassroom, @Param("status") String status);

    @Query("SELECT r FROM Register r WHERE r.classroom.id = :idClassroom")
    Page<Register> findAllByIdClassroom(@Param("idClassroom") String idClassroom, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count "
                    + "from registers_students rs where rs.register_id = :idRegister "
                    + "group by rs.emotion "
    )
    public List<ClassroomSummaryGlobal> findSummary(@Param("idRegister") String idRegister);
}