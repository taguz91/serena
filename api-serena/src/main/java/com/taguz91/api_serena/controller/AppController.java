package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.request.LoginRequest;
import com.taguz91.api_serena.api.request.UpdateRequest;
import com.taguz91.api_serena.api.request.RegisterTeacherRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.SessionInfo;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.service.contracts.LoginService;
import com.taguz91.api_serena.service.contracts.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController("")
@CrossOrigin
public class AppController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> index() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Serena API"));
    }

    @GetMapping("/api")
    public ResponseEntity<MessageResponse> api() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Version 1.0"));
    }

    @PostMapping("/api/v1/create-account")
    public ResponseEntity<Teacher> register(@Valid @RequestBody RegisterTeacherRequest request)
    {
        Teacher teacher =  registerService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacher);
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<Teacher> login(@Valid @RequestBody LoginRequest request)
    {
        Teacher teacher =  loginService.login(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @GetMapping("/api/v1/session/info")
    public ResponseEntity<SessionInfo> sessionInfo(
            @AuthenticationPrincipal Teacher teacher
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(loginService.info(teacher));
    }

    @GetMapping("/api/v1/session/profile")
    public ResponseEntity<Teacher> sessionProfile(
            @AuthenticationPrincipal Teacher teacher
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @PutMapping("/api/v1/session/update")
    public ResponseEntity<Teacher> update(
            @AuthenticationPrincipal Teacher teacher,
            @Valid @RequestBody UpdateRequest request
    ) {
        if (request.getName() != null) {
            teacher.setName(request.getName());
        }

        if (request.getEmail() != null) {
            teacher.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            teacher.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        teacherRepository.save(teacher);

        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }
}
