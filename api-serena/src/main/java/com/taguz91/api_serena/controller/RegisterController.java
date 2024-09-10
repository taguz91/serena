package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.models.Classroom;
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
import com.taguz91.api_serena.api.request.RegisterRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Register;
import com.taguz91.api_serena.repository.RegisterRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/register")
public class RegisterController {

    @Autowired
    private RegisterRepository registerRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<Register>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Register> registers = registerRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(registers));
    }

    @PostMapping("")
    public ResponseEntity<Register> create(@Valid @RequestBody RegisterRequest request) {
        Register register = request.toRegister();
        Register saved = registerRepository.save(register);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Register> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody RegisterRequest request
    ) {
        Register register = registerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El registro no existe"));
        register.setStatus(request.getStatus());

        registerRepository.save(register);

        return ResponseEntity.status(HttpStatus.OK)
                .body(register);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Register> one(
            @PathVariable(value = "id") String id
    ) {
        Register register = registerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El registro no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(register);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        registerRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }
}
