package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.response.*;
import com.taguz91.api_serena.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.StudentRequest;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.repository.StudentRepository;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<Student>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(students));
    }

    @GetMapping("/teacher/{idTeacher}")
    public ResponseEntity<PageResponse<Student>> byTeacher(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @PathVariable(value = "idTeacher") String idTeacher
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findByTeacher(
                idTeacher,
                pageable.getOffset(),
                pageable.getPageSize(),
                pageable
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(students));
    }

    @GetMapping("/teacher/current")
    public ResponseEntity<PageResponse<Student>> byCurrentTeacher(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @AuthenticationPrincipal Teacher teacher
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findByTeacher(
                teacher.getId(),
                pageable.getOffset(),
                pageable.getPageSize(),
                pageable
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(students));
    }

    @GetMapping("/options/teacher/current")
    public ResponseEntity<List<OptionResponse>> optionsByCurrentTeacher(
            @AuthenticationPrincipal Teacher teacher
    ) {
        List<OptionResponse> students = studentRepository.findOptionsByTeacher(
                teacher.getId()
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(students);
    }

    @PostMapping("")
    public ResponseEntity<Student> create(@Valid @RequestBody StudentRequest request) {
        Student student = request.toStudent();
        Student saved = studentRepository.save(student);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody StudentRequest request
    ) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El estudiante no existe"));

        student.setName(request.getName());
        student.setIdentification(request.getIdentification());
        student.setGender(request.getGender());
        student.setReference(request.getReference());

        studentRepository.save(student);

        return ResponseEntity.status(HttpStatus.OK)
                .body(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> one(
            @PathVariable(value = "id") String id
    ) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El estudiante no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(student);
    }

    @GetMapping("/identification/{id}")
    public ResponseEntity<Student> identification(
            @PathVariable(value = "id") String id
    ) {
        Optional<Student> student = studentRepository.findByIdentification(id);

        return ResponseEntity.status(student.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(student.orElseGet(Student::new));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        studentRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }

    @GetMapping("/summary/{idStudent}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summaryByStudent(
            @PathVariable(value = "idStudent") String idStudent,
            @RequestParam Optional<String> periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        periods.isPresent()
                        ? studentRepository.findSummaryByStudentAndAcademicPeriods(
                                idStudent,
                                new ArrayList<>(Arrays.stream(periods.get().split(",")).toList())
                        )
                        : studentRepository.findSummaryByStudent(idStudent)
                );
    }

    @GetMapping("/summary/{idStudent}/subject/{idSubject}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summaryByStudentAndSubject(
            @PathVariable(value = "idStudent") String idStudent,
            @PathVariable(value = "idSubject") String idSubject,
            @RequestParam Optional<String> periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        periods.isPresent()
                        ? studentRepository.findSummaryByStudentAndSubjectAndAcademicPeriods(
                            idStudent,
                            idSubject,
                            new ArrayList<>(Arrays.stream(periods.get().split(",")).toList())
                        ) : studentRepository.findSummaryByStudentAndSubject(
                                idStudent,
                                idSubject
                        )
                );
    }

    @GetMapping("/subjects/{idStudent}")
    public ResponseEntity<List<StudentSubject>> listSubjects(
            @PathVariable(value = "idStudent") String idStudent,
            @RequestParam Optional<String> periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        periods.isPresent()
                            ? studentRepository.findSubjectsAndAcademicPeriods(
                                idStudent,
                                new ArrayList<>(Arrays.stream(periods.get().split(",")).toList())
                            )
                            : studentRepository.findSubjects(
                                idStudent
                            )
                );
    }

    @GetMapping("/classroom/{idClassroom}")
    public ResponseEntity<List<Student>> listByClassroom(
            @PathVariable(value = "idClassroom") String idClassroom
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentRepository.findByClassroom(idClassroom));
    }
}
