package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.exception.ResourceNotFoundException;
import com.taguz91.api_serena.api.request.MethodologyRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Methodology;
import com.taguz91.api_serena.models.MethodologyEmotion;
import com.taguz91.api_serena.repository.MethodologyEmotionRepository;
import com.taguz91.api_serena.repository.MethodologyRepository;
import com.taguz91.api_serena.utils.NanoCombCreator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/methodology")
public class MethodologyController {

    @Autowired
    private MethodologyRepository methodologyRepository;

    @Autowired
    private MethodologyEmotionRepository methodologyEmotionRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<Methodology>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Methodology> methodologies = methodologyRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(methodologies));
    }

    @PostMapping("")
    public ResponseEntity<Methodology> create(
            @Valid @RequestBody MethodologyRequest request
    ) {
        Methodology methodology = methodologyRepository.save(
                request.toMethodology()
        );

        saveEmotions(methodology, request.getEmotions());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(methodology);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Methodology> update(
            @PathVariable(value = "id") String id,
        @Valid @RequestBody MethodologyRequest request
    ) {
        Methodology methodology = methodologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la metologia que se busca."));

        methodology.setName(request.getName());
        methodology.setSummary(request.getSummary());

        methodologyRepository.save(methodology);
        methodologyEmotionRepository.deleteByMethodologyId(methodology.getId());

        saveEmotions(methodology, request.getEmotions());

        return ResponseEntity.status(HttpStatus.OK)
                .body(methodology);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Methodology> one(
        @PathVariable(value = "id") String id
    ) {
        Methodology methodology = methodologyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la metologia que se busca."));

        return ResponseEntity.status(HttpStatus.OK)
                .body(methodology);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        methodologyEmotionRepository.deleteByMethodologyId(id);
        methodologyRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }

    private void saveEmotions(Methodology methodology, List<String> emotions) {
        List<MethodologyEmotion> methodologyEmotions = emotions.stream().map((emotion) -> {
            MethodologyEmotion methodologyEmotion = new MethodologyEmotion();

            methodologyEmotion.setId((new NanoCombCreator()).create().toString());
            methodologyEmotion.setMethodology(methodology);
            methodologyEmotion.setEmotion(emotion);

            return methodologyEmotion;
        }).toList();

        methodologyEmotionRepository.saveAll(methodologyEmotions);
    }
}
