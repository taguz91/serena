package com.taguz91.api_serena.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.taguz91.api_serena.models.RegisterStudent;

import java.util.List;
import java.util.Optional;

@Repository

public interface RegisterStudentRepository extends JpaRepository<RegisterStudent, String> {

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.register.id = :idRegister")
    public List<RegisterStudent> findByIdRegister(@Param("idRegister") String idRegister);

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.register.id = :idRegister AND rs.register.classroom.teacher.id = :idTeacher")
    public List<RegisterStudent> findByIdRegisterAndTeacher(
            @Param("idRegister") String idRegister,
            @Param("idTeacher") String idTeacher
    );

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.student.id = :idStudent")
    public List<RegisterStudent> findByIdStudent(@Param("idStudent") String idStudent);

    @Query(value = "SELECT rs FROM RegisterStudent rs WHERE rs.student_id = :idStudent AND rs.status = 'inscription' ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    public Optional<RegisterStudent> findByLastByIdStudent(@Param("idStudent") String idStudent);

    static final String QUERY_BY_CLASSROOM = " from registers r "
            + "join registers_students rs on rs.register_id = r.id "
            + "join students s on rs.student_id = s.id "
            + "where classroom_id = :idClassroom and s.\"name\" = '' ";

    @Query(
            value = "SELECT rs.* " + QUERY_BY_CLASSROOM
                    + " ORDER BY s.created_at DESC LIMIT :limitParam OFFSET :offset "
                    + "\n-- #pageable\n",
            countQuery = "SELECT count(*) " + QUERY_BY_CLASSROOM,
            nativeQuery = true
    )
    Page<RegisterStudent> findPendingInscriptionClassroom(
            @Param("idClassroom") String idClassroom,
            @Param("offset") long offset,
            @Param("limitParam") int limitParam,
            Pageable pageable
    );
}
