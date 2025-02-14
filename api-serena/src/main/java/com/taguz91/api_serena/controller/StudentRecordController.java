package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.StudentRecordRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.StudentRecord;
import com.taguz91.api_serena.repository.StudentRecordRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/student-records")
public class StudentRecordController {

    @Autowired
    private StudentRecordRepository studentRecordRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<StudentRecord>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<StudentRecord> studentRecords = studentRecordRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(studentRecords));
    }

    @PostMapping("")
    public ResponseEntity<StudentRecord> create(@Valid @RequestBody StudentRecordRequest request) {
        StudentRecord studentRecord = request.toStudentRecord();
        StudentRecord saved = studentRecordRepository.save(studentRecord);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentRecord> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody StudentRecordRequest request
    ) {
        StudentRecord studentRecord = studentRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El registro del estudiante no existe"));

        studentRecord.setReference(request.getReference());
        studentRecord.setIdentification(request.getIdentification());
        studentRecord.setName(request.getName());
        studentRecord.setGender(request.getGender());
        studentRecord.setPhoto(request.getPhoto());

        studentRecordRepository.save(studentRecord);

        return ResponseEntity.status(HttpStatus.OK)
                .body(studentRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentRecord> one(
            @PathVariable(value = "id") String id
    ) {
        StudentRecord studentRecord = studentRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El registro del estudiante no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(studentRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        studentRecordRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }
}
