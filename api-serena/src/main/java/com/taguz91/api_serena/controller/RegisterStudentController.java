package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.exception.ResourceNotFoundException;
import com.taguz91.api_serena.api.request.CreateDuplicateRegisterStudentRequest;
import com.taguz91.api_serena.api.request.CreateRegisterStudentRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.service.contracts.CreateStudentRegister;
import com.taguz91.api_serena.service.contracts.DownloadImageService;
import com.taguz91.api_serena.utils.ImageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/register-student")
public class RegisterStudentController {

    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @Autowired
    private CreateStudentRegister createStudentRegister;

    @Autowired
    private DownloadImageService downloadImageService;

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

    @GetMapping("/register/{idRegister}")
    public ResponseEntity<List<RegisterStudent>> byIdRegister(
            @PathVariable(value = "idRegister") String idRegister
    ) {
        List<RegisterStudent> registerStudents = registerStudentRepository.findByIdRegister(idRegister);

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudents);
    }

    @GetMapping("/register/current/{idRegister}")
    public ResponseEntity<List<RegisterStudent>> byCurrentTeacherIdRegister(
            @PathVariable(value = "idRegister") String idRegister,
            @AuthenticationPrincipal Teacher teacher
    ) {
        List<RegisterStudent> registerStudents = registerStudentRepository.findByIdRegisterAndTeacher(
                idRegister,
                teacher.getId()
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudents);
    }

    @GetMapping("/student/{idRegister}")
    public ResponseEntity<List<RegisterStudent>> byIdStudent(
            @PathVariable(value = "idStudent") String idStudent
    ) {
        List<RegisterStudent> registerStudents = registerStudentRepository.findByIdStudent(idStudent);

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudents);
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
        RegisterStudent registerStudent = createStudentRegister.create(photo, idRegister, null);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerStudent);
    }

    @PostMapping("/create/base64")
    public ResponseEntity<RegisterStudent> createBase64(
            @Valid @RequestBody CreateRegisterStudentRequest request
    ) {
        RegisterStudent registerStudent = createStudentRegister.create(
                ImageUtil.base64ToMultipartFile("students-checks", request.getPhoto()),
                request.getIdRegister(),
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerStudent);
    }

    @PostMapping("/create/inscription")
    public ResponseEntity<RegisterStudent> createInscription(
            @Valid @RequestBody CreateDuplicateRegisterStudentRequest request
    ) {
        RegisterStudent registerStudent = request.getPhoto() == null
            ? createStudentRegister.duplicate(request.getIdStudent(), request.getIdRegister())
            : createStudentRegister.create(
                    ImageUtil.base64ToMultipartFile("students-checks", request.getPhoto()),
                    request.getIdRegister(),
                    (new Student()).setId(request.getIdStudent())
            );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerStudent);
    }

    @GetMapping("/exists/inscription/{idRegister}/{idStudent}")
    public ResponseEntity<RegisterStudent> existInscription(
            @PathVariable(value = "idRegister") String idRegister,
            @PathVariable(value = "idStudent") String idStudent
    ) {
        RegisterStudent registerStudent = registerStudentRepository.findInscriptionByIdRegisterAndIdStudent(
            idRegister,
            idStudent
        ).orElseThrow(ResourceNotFoundException::new);

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisterStudent> one(
            @PathVariable(value = "id") String id
    ) {
        RegisterStudent registerStudent = registerStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El registro del estudiante no existe"));

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

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> showPhoto(@PathVariable(value = "id") String id) throws IOException {
        RegisterStudent registerStudent = registerStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El registro del estudiante no existe"));

        byte[] bytes = downloadImageService.download(registerStudent.getPhoto());
        String[] paths = registerStudent.getPhoto().split("/");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", paths[paths.length - 1]);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(bytes);
    }
}