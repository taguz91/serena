package com.taguz91.api_serena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.repository.StudentRepository;

import jakarta.validation.Valid;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        studentRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }
}
