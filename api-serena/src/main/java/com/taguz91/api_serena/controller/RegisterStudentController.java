package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.CreateRegisterStudentRequest;
import com.taguz91.api_serena.api.request.RegisterStudentRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.service.contracts.CreateStudentRegister;
import com.taguz91.api_serena.utils.ImageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.print.attribute.standard.Media;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/register-student")
public class RegisterStudentController {

    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @Autowired
    private CreateStudentRegister createStudentRegister;

    @GetMapping("")
    public ResponseEntity<PageResponse<RegisterStudent>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RegisterStudent> registerStudents = registerStudentRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<RegisterStudent>(registerStudents));
    }

    @PostMapping(
            value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegisterStudent> create(
            @Valid @RequestParam("photo") MultipartFile photo,
            @Valid @RequestParam("idRegister") String idRegister
    ) {
        RegisterStudent registerStudent = createStudentRegister.create(photo, idRegister);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerStudent);
    }

    @PostMapping("/create/base64")
    public ResponseEntity<RegisterStudent> createBase64(
            @Valid @RequestBody CreateRegisterStudentRequest request
    ) {
        RegisterStudent registerStudent = createStudentRegister.create(
                ImageUtil.base64ToMultipartFile("students-checks", request.getPhoto()),
                request.getIdRegister()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
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