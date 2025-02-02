package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.request.TeacherRequest;
import com.taguz91.api_serena.api.request.UpdateRequest;
import com.taguz91.api_serena.api.response.ClassroomSummaryGlobal;
import com.taguz91.api_serena.api.response.MessageResponse;
import com.taguz91.api_serena.api.response.OptionResponse;
import com.taguz91.api_serena.api.response.PageResponse;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.utils.Shared;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "El profesor no existe"));

        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setIsActive(request.isActive());
        teacher.setIsAdmin(request.isAdmin());

        teacherRepository.save(teacher);

        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> one(
            @PathVariable(value = "id") String id
    ) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "El profesor no existe"));


        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(
            @PathVariable(value = "id") String id
    ) {
        List<RegisterStudent> list = registerStudentRepository.findByIdTeacher(
                id
        );

        if (list.toArray().length > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Ya tiene registros creados, no se permite eliminar"));
        }

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

    @PutMapping("/toggle/activate/{idTeacher}")
    public ResponseEntity<Teacher> toggleActivation(
            @PathVariable(value = "idTeacher") String idTeacher
    ) {
        Teacher teacher = teacherRepository.findById(idTeacher)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "El profesor no existe"));

        teacher.setIsActive(!teacher.getIsActive());

        teacherRepository.save(teacher);

        return ResponseEntity.status(HttpStatus.OK)
                .body(teacher);
    }

    @PutMapping("/change/password/{idTeacher}")
    public ResponseEntity<Teacher> updatePassword(
            @PathVariable(value = "idTeacher") String idTeacher,
            @Valid @RequestBody UpdateRequest request
    ) {
        Teacher teacher = teacherRepository.findById(idTeacher)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "El profesor no existe"));

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
