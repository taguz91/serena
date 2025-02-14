package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.criteria.CriteriaHelper;
import com.taguz91.api_serena.api.criteria.builder.AcademicPeriodSpecificationBuilder;
import com.taguz91.api_serena.api.request.AcademicPeriodRequest;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.AcademicPeriod;
import com.taguz91.api_serena.models.Carrera;
import com.taguz91.api_serena.repository.AcademicPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("api/v1/academic-period")
public class AcademicPeriodController {

    @Autowired
    private AcademicPeriodRepository academicPeriodRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<AcademicPeriod>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        CriteriaHelper<AcademicPeriod> criteriaHelper = new CriteriaHelper<>(
                new AcademicPeriodSpecificationBuilder()
        );

        Specification<AcademicPeriod> spec = criteriaHelper.build(search);
        Pageable pageable = PageRequest.of(page, size);
        Page<AcademicPeriod> academicPeriods = academicPeriodRepository.findAll(
                spec,
                pageable
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(academicPeriods));
    }

    @PostMapping("")
    public ResponseEntity<AcademicPeriod> create(@Valid @RequestBody AcademicPeriodRequest request) {
        AcademicPeriod academicPeriod = request.toAcademicPeriod();
        AcademicPeriod saved = academicPeriodRepository.save(academicPeriod);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicPeriod> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody AcademicPeriodRequest request
    ) {
        AcademicPeriod academicPeriod = academicPeriodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El periodo académico no existe"));

        academicPeriod.setName(request.getName());
        academicPeriod.setReference(request.getReference());
        academicPeriod.setIsActive(request.getIsActive());
        academicPeriod.setCarrera(
                (new Carrera()).setId(request.getIdCarrera())
        );

        academicPeriodRepository.save(academicPeriod);

        return ResponseEntity.status(HttpStatus.OK)
                .body(academicPeriod);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicPeriod> one(
            @PathVariable(value = "id") String id
    ) {
        AcademicPeriod academicPeriod = academicPeriodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El periodo académico no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(academicPeriod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        academicPeriodRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }

    @GetMapping("/all/options")
    public ResponseEntity<List<OptionResponse>> options() {
        List<OptionResponse> options = academicPeriodRepository.findAllOptions();

        return ResponseEntity.status(HttpStatus.OK)
                .body(options);
    }
}
