package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    static final String QUERY_BY_TEACHER = " FROM public.students s "
            + "join registers_students rs on rs.student_id = s.id "
            + "join registers r on r.id  = rs.register_id "
            + "join classrooms c on c.id = r.classroom_id "
            + "where c.teacher_id = :idTeacher "
            + " ";

    Optional<Student> findByIdentification(@Param("identification") String identification);

    @Query(
            value = "SELECT s.id, s.identification, s.name, s.gender, s.reference, s.created_at, s.updated_at, s.created_by, s.updated_by, s.is_deleted "
                    + QUERY_BY_TEACHER
                    +  " ORDER BY s.name ASC LIMIT :limitParam OFFSET :offset "
                    + "\n-- #pageable\n",
            countQuery = "SELECT count(*) " + QUERY_BY_TEACHER,
            nativeQuery = true
    )
    Page<Student> findByTeacher(
            @Param("idTeacher") String idTeacher,
            @Param("offset") long offset,
            @Param("limitParam") int limitParam,
            Pageable pageable
    );

    @Query(
            value = "SELECT s.id as value, s.name as label "
                    + QUERY_BY_TEACHER
                    +  " ORDER BY s.name ASC",
            nativeQuery = true
    )
    List<OptionResponse> findOptionsByTeacher(@Param("idTeacher") String idTeacher);


    static final String QUERY_BY_CLASSROOM = " from registers r "
                + "join registers_students rs on rs.register_id = r.id "
                + "join students s on rs.student_id = s.id "
                + "where classroom_id = :idClassroom and s.\"name\" = '' ";

    @Query(
            value = "SELECT s.* " + QUERY_BY_CLASSROOM
            + " ORDER BY s.created_at DESC LIMIT :limitParam OFFSET :offset "
            + "\n-- #pageable\n",
            countQuery = "SELECT count(*) " + QUERY_BY_CLASSROOM,
            nativeQuery = true
    )
    Page<Student> findPendingInscriptionByClassroom(
            @Param("idClassroom") String idClassroom,
            @Param("offset") long offset,
            @Param("limitParam") int limitParam,
            Pageable pageable
    );
}