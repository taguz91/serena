package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.request.ClassRecordRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.ClassRecord;
import com.taguz91.api_serena.repository.ClassRecordRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/class-records")
public class ClassRecordController {

    @Autowired
    private ClassRecordRepository classRecordRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<ClassRecord>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassRecord> classRecords = classRecordRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(classRecords));
    }

    @PostMapping("")
    public ResponseEntity<ClassRecord> create(@Valid @RequestBody ClassRecordRequest request) {
        ClassRecord classRecord = request.toClassRecord();
        ClassRecord saved = classRecordRepository.save(classRecord);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRecord> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody ClassRecordRequest request
    ) {
        ClassRecord classRecord = classRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassRecord not found"));

        classRecord.setStudentReference(request.getStudentReference());
        classRecord.setTeacherReference(request.getTeacherReference());

        classRecordRepository.save(classRecord);

        return ResponseEntity.status(HttpStatus.OK)
                .body(classRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRecord> one(
            @PathVariable(value = "id") String id
    ) {
        ClassRecord classRecord = classRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClassRecord not found"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(classRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        classRecordRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Deleted successfully"));
    }
}
