package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.models.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {

    @Query("SELECT c FROM Classroom c WHERE c.name like %:criteria% or c.academicPeriod.name like %:criteria% or c.teacher.name like %:criteria% or c.teacher.email like %:criteria% or c.subject.name like %:criteria%")
    public Page<Classroom> findAllPageable(@Param("criteria") String criteria, Pageable pageable);

    @Query("SELECT c FROM Classroom c WHERE c.teacher.id = :idTeacher AND c.academicPeriod.isActive = true AND c.isDeleted = false ORDER BY c.subject.name")
    public List<Classroom> findAllByTeacherAcademicPeriodActive(@Param("idTeacher") String idTeacher);

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
            + "join registers_students rs on rs.register_id = r.id "
            + "where classroom_id = :idClassroom "
            + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummary(@Param("idClassroom") String idClassroom);

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
                    + "join registers_students rs on rs.register_id = r.id "
                    + "join classrooms c on c.id = r.classroom_id "
                    + "where r.classroom_id = :idClassroom "
                    + "and c.academic_period_id IN( :academicPeriods ) "
                    + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummaryByAcademicPeriod(
            @Param("idClassroom") String idClassroom,
            @Param("academicPeriods") Collection<String> academicPeriods
    );

    @Query(
            nativeQuery = true,
            value = "select rs.emotion, count(rs.emotion) as count from registers r "
                    + "join registers_students rs on rs.register_id = r.id "
                    + "join classrooms c on c.id = r.classroom_id "
                    + "where r.classroom_id = :idClassroom "
                    + "and c.academic_period_id IN( :academicPeriods ) "
                    + "and r.created_at >= :start "
                    + "and r.created_at <= :end "
                    + "group by rs.emotion"
    )
    public List<ClassroomSummaryGlobal> findSummaryByAcademicPeriodAndDates(
            @Param("idClassroom") String idClassroom,
            @Param("academicPeriods") Collection<String> academicPeriods,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
