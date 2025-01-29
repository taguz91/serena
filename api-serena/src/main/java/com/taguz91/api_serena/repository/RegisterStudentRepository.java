package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.taguz91.api_serena.models.RegisterStudent;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterStudentRepository extends JpaRepository<RegisterStudent, String> {

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.register.id = :idRegister")
    public List<RegisterStudent> findByIdRegister(@Param("idRegister") String idRegister);

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.register.id = :idRegister AND rs.student.id = :idStudent AND rs.status = 'inscription'")
    public Optional<RegisterStudent> findInscriptionByIdRegisterAndIdStudent(
            @Param("idRegister") String idRegister,
            @Param("idStudent") String idStudent
    );

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.register.id = :idRegister AND rs.register.classroom.teacher.id = :idTeacher")
    public List<RegisterStudent> findByIdRegisterAndTeacher(
            @Param("idRegister") String idRegister,
            @Param("idTeacher") String idTeacher
    );

    @Query("SELECT rs FROM RegisterStudent rs WHERE rs.student.id = :idStudent")
    public List<RegisterStudent> findByIdStudent(@Param("idStudent") String idStudent);

    @Query(value = "SELECT rs.* FROM registers_students rs WHERE rs.student_id = :idStudent AND rs.status = 'inscription' ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
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

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
                    + "join registers_students rs on rs.register_id = r.id "
                    + "join classrooms c on c.id = r.classroom_id "
                    + "where c.teacher_id = :idTeacher "
                    + "and c.academic_period_id IN( :academicPeriods ) "
                    + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummaryByTeacherAndPeriods(
            @Param("idTeacher") String idTeacher,
            @Param("academicPeriods") Collection<String> academicPeriods
    );

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
                    + "join registers_students rs on rs.register_id = r.id "
                    + "join classrooms c on c.id = r.classroom_id "
                    + "where c.subject_id = :idSubject "
                    + "and c.academic_period_id IN( :academicPeriods ) "
                    + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummaryBySubjectAndPeriods(
            @Param("idSubject") String idSubject,
            @Param("academicPeriods") Collection<String> academicPeriods
    );

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
                    + "join registers_students rs on rs.register_id = r.id "
                    + "join classrooms c on c.id = r.classroom_id "
                    + "where c.subject_id = :idSubject "
                    + "and c.academic_period_id IN( :academicPeriods ) "
                    + "and r.created_at >= :start "
                    + "and r.created_at <= :end "
                    + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummaryBySubjectAndPeriodsAndDates(
            @Param("idSubject") String idSubject,
            @Param("academicPeriods") Collection<String> academicPeriods,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
                    + "join registers_students rs on rs.register_id = r.id "
                    + "join classrooms c on c.id = r.classroom_id "
                    + "where c.teacher_id = :idTeacher "
                    + "and c.academic_period_id IN( :academicPeriods ) "
                    + "and r.created_at >= :start "
                    + "and r.created_at <= :end "
                    + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummaryByTeacherAndPeriodsAndDates(
            @Param("idTeacher") String idTeacher,
            @Param("academicPeriods") Collection<String> academicPeriods,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
