package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.request.LoginRequest;
import com.taguz91.api_serena.api.request.RegisterTeacherRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.service.contracts.LoginService;
import com.taguz91.api_serena.service.contracts.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("")
@CrossOrigin
public class AppController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

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
}
