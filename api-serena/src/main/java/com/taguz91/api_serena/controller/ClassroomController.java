package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.ClassroomRequest;
import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.AcademicPeriod;
import com.taguz91.api_serena.models.Classroom;
import com.taguz91.api_serena.models.Subject;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomRepository classroomRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<Classroom>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Classroom> classrooms = classroomRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(classrooms));
    }

    @PostMapping("")
    public ResponseEntity<Classroom> create(@Valid @RequestBody ClassroomRequest request) {
        Classroom classroom = request.toClassroom();
        Classroom saved = classroomRepository.save(classroom);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody ClassroomRequest request
    ) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El aula no existe"));

        classroom.setAcademicPeriod(
                (new AcademicPeriod()).setId(request.getIdAcademicPeriod())
        );
        classroom.setTeacher(
                (new Teacher()).setId(request.getIdTeacher())
        );
        classroom.setSubject(
                (new Subject()).setId(request.getIdSubject())
        );

        classroomRepository.save(classroom);

        return ResponseEntity.status(HttpStatus.OK)
                .body(classroom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> one(
            @PathVariable(value = "id") String id
    ) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El aula no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(classroom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        classroomRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }

    @GetMapping("/teacher/{idTeacher}")
    public ResponseEntity<List<Classroom>> teacherActive(
            @PathVariable(value = "idTeacher") String idTeacher
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(classroomRepository.findAllByTeacherAcademicPeriodActive(idTeacher));
    }

    @GetMapping("/teacher/current")
    public ResponseEntity<List<Classroom>> teacherCurrent(
            @AuthenticationPrincipal Teacher teacher
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(classroomRepository.findAllByTeacherAcademicPeriodActive(
                        teacher.getId()
                ));
    }

    @GetMapping("/summary/{idClassroom}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> emotionSummary(
            @PathVariable(value = "idClassroom") String idClassroom,
            @RequestParam Optional<String> periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        periods.isPresent()
                        ? classroomRepository.findSummaryByAcademicPeriod(
                                idClassroom,
                                new ArrayList<>(Arrays.stream(periods.get().split(",")).toList())
                        )
                        : classroomRepository.findSummary(idClassroom)
                );
    }
}
