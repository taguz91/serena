package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class AppController {

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
}
