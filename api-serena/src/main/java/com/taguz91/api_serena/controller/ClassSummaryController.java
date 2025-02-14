package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.ClassSummaryRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.ClassSummary;
import com.taguz91.api_serena.repository.ClassSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/class-summary")
public class ClassSummaryController {

    @Autowired
    private ClassSummaryRepository classSummaryRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<ClassSummary>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
        Page<ClassSummary> classSummaries = classSummaryRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(classSummaries));
    }

    @PostMapping("")
    public ResponseEntity<ClassSummary> create(@Valid @RequestBody ClassSummaryRequest request) {
        ClassSummary classSummary = request.toClassSummary();
        ClassSummary saved = classSummaryRepository.save(classSummary);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSummary> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody ClassSummaryRequest request
    ) {
        ClassSummary classSummary = classSummaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El resumen de la clase no existe"));

        classSummary.setAvg_emotion(request.getAvgEmotion());
        classSummary.setMin_emotion(request.getMinEmotion());
        classSummary.setMax_emotion(request.getMaxEmotion());

        classSummaryRepository.save(classSummary);

        return ResponseEntity.status(HttpStatus.OK)
                .body(classSummary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSummary> one(
            @PathVariable(value = "id") String id
    ) {
        ClassSummary classSummary = classSummaryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El resumen de la clase no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(classSummary);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        classSummaryRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }
}
