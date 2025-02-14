package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.ClassReportRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.ClassReport;
import com.taguz91.api_serena.repository.ClassReportRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taguz91.api_serena.models.Register;
import com.taguz91.api_serena.models.Student;



@RestController
@RequestMapping("api/v1/class-report")
public class ClassReportController {

    @Autowired
    private ClassReportRepository classReportRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<ClassReport>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ClassReport> classReports = classReportRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(classReports));
    }

    @PostMapping("")
    public ResponseEntity<ClassReport> create(@Valid @RequestBody ClassReportRequest request) {
        ClassReport classReport = request.toClassReport();
        ClassReport saved = classReportRepository.save(classReport);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassReport> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody ClassReportRequest request
    ) {
        ClassReport classReport = classReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El reporte de clase no existe"));

        classReport.setStartEmotion(request.getStartEmotion())
                .setEndEmotion(request.getEndEmotion())
                .setRegister(new Register().setId(request.getRegisterId()))
                .setStudent(new Student().setId(request.getStudentId()));

        classReportRepository.save(classReport);

        return ResponseEntity.status(HttpStatus.OK)
                .body(classReport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassReport> one(
            @PathVariable(value = "id") String id
    ) {
        ClassReport classReport = classReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El reporte de clase no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(classReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        classReportRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }
}
