package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.request.InscriptionRequest;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Inscription;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.repository.InscriptionRepository;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("api/v1/inscription")
public class InscriptionController {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @GetMapping("/classroom/{idClassroom}")
    public ResponseEntity<Inscription> detail(
            @PathVariable(value = "idClassroom") String idClassroom
    ) {
        Inscription inscription = inscriptionRepository.findByIdClassroom(
                idClassroom
        ).orElseThrow(() -> new HttpClientErrorException(
                HttpStatus.NOT_FOUND,
                "No se encontro el registro"
        ));

        return ResponseEntity.status(HttpStatus.OK)
                .body(inscription);
    }

    @GetMapping("/classroom/{idClassroom}/pending/students")
    public ResponseEntity<PageResponse<Student>> studentsPendingInscription(
            @PathVariable("idClassroom")  String idClassroom,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Student> students = studentRepository.findPendingInscriptionByClassroom(
                idClassroom,
                pageable.getOffset(),
                pageable.getPageSize(),
                pageable
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(students));
    }

    @GetMapping("/classroom/{idClassroom}/pending/register/students")
    public ResponseEntity<PageResponse<RegisterStudent>> registerStudentsPendingInscription(
            @PathVariable("idClassroom")  String idClassroom,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<RegisterStudent> students = registerStudentRepository.findPendingInscriptionClassroom(
                idClassroom,
                pageable.getOffset(),
                pageable.getPageSize(),
                pageable
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(students));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscription> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody InscriptionRequest request
    )  {
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(
                        () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "404")
                );

        inscription.setPhotos(request.getPhotos());
        inscription.setStudents(request.getStudents());

        return ResponseEntity.status(HttpStatus.OK)
                .body(inscription);
    }
}
