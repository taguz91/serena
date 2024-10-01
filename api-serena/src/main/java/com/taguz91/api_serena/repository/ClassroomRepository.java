package com.taguz91.api_serena.repository;

import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.models.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String> {

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
}
