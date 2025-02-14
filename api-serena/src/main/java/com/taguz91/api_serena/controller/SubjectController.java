package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.criteria.CriteriaHelper;
import com.taguz91.api_serena.api.criteria.builder.SubjectSpecificationBuilder;
import com.taguz91.api_serena.api.request.SubjectRequest;
import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Subject;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.repository.SubjectRepository;
import com.taguz91.api_serena.utils.Shared;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/subject")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<Subject>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        CriteriaHelper<Subject> criteriaHelper = new CriteriaHelper<>(
                new SubjectSpecificationBuilder()
        );

        Specification<Subject> spec = criteriaHelper.build(search);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Subject> subjects = subjectRepository.findAll(spec, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<Subject>(subjects));
    }

    @PostMapping("")
    public ResponseEntity<Subject> create(@Valid @RequestBody SubjectRequest request) {
        Subject subject = request.toSubject();
        Subject saved = subjectRepository.save(subject);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody SubjectRequest request
    ) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La materia no existe"));

        subject.setName(request.getName());


        subjectRepository.save(subject);

        return ResponseEntity.status(HttpStatus.OK)
                .body(subject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> one(
            @PathVariable(value = "id") String id
    ) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La materia no existe"));

        return ResponseEntity.status(HttpStatus.OK)
                .body(subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        subjectRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con éxito"));
    }

    @GetMapping("/all/options")
    public ResponseEntity<List<OptionResponse>> options()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subjectRepository.findAllOptions());
    }

    @GetMapping("/summary/{idSubject}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summarySubject(
            @PathVariable(value = "idSubject") String idSubject,
            @RequestParam String periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudentRepository.findSummaryBySubjectAndPeriods(
                        idSubject,
                        new ArrayList<>(Arrays.stream(periods.split(",")).toList())
                ));
    }

    @GetMapping("/summary/{idSubject}/{start}/{end}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summarySubject(
            @PathVariable(value = "idSubject") String idSubject,
            @RequestParam String periods,
            @PathVariable(value = "start") String start,
            @PathVariable(value = "end") String end
    ) {
        LocalDateTime startDate = Shared.startDate(start);
        LocalDateTime endDate = Shared.endDate(end);

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudentRepository.findSummaryBySubjectAndPeriodsAndDates(
                        idSubject,
                        new ArrayList<>(Arrays.stream(periods.split(",")).toList()),
                        startDate,
                        endDate
                ));
    }
}
