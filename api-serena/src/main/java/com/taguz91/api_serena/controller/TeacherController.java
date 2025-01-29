package com.taguz91.api_serena.controller;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.TeacherRequest;
import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.utils.Shared;
import jakarta.validation.Valid;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @GetMapping("")
    public ResponseEntity<PageResponse<Teacher>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teachers = teacherRepository.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<Teacher>(teachers));
    }

    @PostMapping("")
    public ResponseEntity<Teacher> create(@Valid @RequestBody TeacherRequest request) {
        Teacher teacher = request.toTeacher();
        Teacher saved = teacherRepository.save(teacher);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(
            @PathVariable(value = "id") String id,
            @Valid @RequestBody TeacherRequest request
    ) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El profesor no existe"));

        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setIsActive(request.isActive());

        teacherRepository.save(teacher);

        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> one(
            @PathVariable(value = "id") String id
    ) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El profesor no existe"));


        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        teacherRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Borrado con exito"));
    }

    @GetMapping("/all/options")
    public ResponseEntity<List<OptionResponse>> options()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(teacherRepository.findAllOptions());
    }

    @GetMapping("/summary/current")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summaryCurrent(
            @AuthenticationPrincipal Teacher teacher,
            @RequestParam String periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudentRepository.findSummaryByTeacherAndPeriods(
                        teacher.getId(),
                        new ArrayList<>(Arrays.stream(periods.split(",")).toList())
                ));
    }

    @GetMapping("/summary/current/{start}/{end}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summaryTeacherByDate(
            @AuthenticationPrincipal Teacher teacher,
            @RequestParam String periods,
            @PathVariable(value = "start") String start,
            @PathVariable(value = "end") String end
    ) {
        return this.summaryTeacherByDate(
                teacher.getId(),
                periods,
                start,
                end
        );
    }

    @GetMapping("/summary/{idTeacher}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summaryTeacher(
            @PathVariable(value = "idTeacher") String idTeacher,
            @RequestParam String periods
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudentRepository.findSummaryByTeacherAndPeriods(
                        idTeacher,
                        new ArrayList<>(Arrays.stream(periods.split(",")).toList())
                ));
    }

    @GetMapping("/summary/{idTeacher}/{start}/{end}")
    public ResponseEntity<List<ClassroomSummaryGlobal>> summaryTeacherByDate(
            @PathVariable(value = "idTeacher") String idTeacher,
            @RequestParam String periods,
            @PathVariable(value = "start") String start,
            @PathVariable(value = "end") String end
    ) {
        LocalDateTime startDate = Shared.startDate(start);
        LocalDateTime endDate = Shared.endDate(end);

        return ResponseEntity.status(HttpStatus.OK)
                .body(registerStudentRepository.findSummaryByTeacherAndPeriodsAndDates(
                        idTeacher,
                        new ArrayList<>(Arrays.stream(periods.split(",")).toList()),
                        startDate,
                        endDate
                ));
    }
}
