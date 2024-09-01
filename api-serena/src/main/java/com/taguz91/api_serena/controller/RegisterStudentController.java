package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.RegisterStudentRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("api/v1/register-student")
public class RegisterStudentController {

    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<RegisterStudent>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegisterStudent> registerStudents = registerStudentRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<RegisterStudent>(registerStudents));
    }

    @PostMapping("")
    public ResponseEntity<RegisterStudent> create(@Valid @RequestBody RegisterStudentRequest request) {
        RegisterStudent registerStudent = request.toRegisterStudent();
        RegisterStudent saved = registerStudentRepository.save(registerStudent);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegisterStudent> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody RegisterStudentRequest request
    ) {
        RegisterStudent registerStudent = registerStudentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El registro del estudiante no existe"));

        registerStudent.setPhoto(request.getPhoto());
        registerStudent.setEmotion(request.getEmotion());
        registerStudent.setRegister(request.getRegisterId());
        registerStudent.setStudent(request.getStudentId());

        registerStudentRepository.save(registerStudent);

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterStudent> one(
            @PathVariable(value = "id") String id
    ) {
        RegisterStudent registerStudent = registerStudentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El registro del estudiante no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        registerStudentRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con exito"));
    }
}