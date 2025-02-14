package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.StudentReportRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.StudentReport;
import com.taguz91.api_serena.repository.StudentReportRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taguz91.api_serena.models.Classroom;
import com.taguz91.api_serena.models.Student;


@RestController
@RequestMapping("api/v1/student-report")
public class StudentReportController {

    @Autowired
    private StudentReportRepository studentReportRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<StudentReport>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<StudentReport> studentReports = studentReportRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(studentReports));
    }

    @PostMapping("")
    public ResponseEntity<StudentReport> create(@Valid @RequestBody StudentReportRequest request) {
        StudentReport studentReport = request.toStudentReport();
        StudentReport saved = studentReportRepository.save(studentReport);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentReport> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody StudentReportRequest request
    ) {
        StudentReport studentReport = studentReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El reporte de estudiante no existe"));

        studentReport.setDate(request.getDate())
                .setStartEmotion(request.getStartEmotion())
                .setEndEmotion(request.getEndEmotion())
                .setClassroom(new Classroom().setId(request.getClassroomId()))
                .setStudent(new Student().setId(request.getStudentId()));

        studentReportRepository.save(studentReport);

        return ResponseEntity.status(HttpStatus.OK)
                .body(studentReport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentReport> one(
            @PathVariable(value = "id") String id
    ) {
        StudentReport studentReport = studentReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El reporte de estudiante no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(studentReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        studentReportRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }
}
