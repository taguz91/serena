package com.taguz91.api_serena.controller;

import com.taguz91.api_serena.api.request.MockRequest;
import com.taguz91.api_serena.models.*;
import com.taguz91.api_serena.repository.*;
import com.taguz91.api_serena.service.CreateStudentRegisterImp;
import com.taguz91.api_serena.utils.NanoCombCreator;
import com.taguz91.api_serena.utils.Shared;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/mocks")
public class MockController {

    @Autowired
    private AcademicPeriodRepository academicPeriodRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private RegisterStudentRepository registerStudentRepository;

    @PostMapping("")
    public ResponseEntity<Integer> mockStadistic(
            @Valid @RequestBody MockRequest request
    ) {
        Carrera carrera = carreraRepository.findById(request.getIdCarrier())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe la carrera"));

        Optional<AcademicPeriod> academicPeriod = academicPeriodRepository.findActiveByIdCarrier(
                request.getIdCarrier()
        );

        if (academicPeriod.isPresent()) {
            AcademicPeriod actualAcademicPeriod = academicPeriod.get();
            actualAcademicPeriod.setIsActive(false);

            academicPeriodRepository.save(actualAcademicPeriod);
        }

        LocalDateTime time = LocalDateTime.now();

        AcademicPeriod newAcademicPeriod = new AcademicPeriod();
        newAcademicPeriod.setCarrera(carrera);
        newAcademicPeriod.setIsActive(true);
        newAcademicPeriod.setName("MOCK " + time.getYear() + "/" + time.getMonth() + "/" + time.getDayOfMonth());
        newAcademicPeriod.setId(new NanoCombCreator().create().toString());

        AcademicPeriod savedAcademicPeriod = academicPeriodRepository.save(newAcademicPeriod);

        List<Subject> subjects = subjectRepository.findAll();
        List<Teacher> teachers = teacherRepository.findAll();

        List<Classroom> classrooms = new ArrayList<>();

        int course = 1;
        int teacher = 0;
        for (int i = 0; i < subjects.size(); i++) {
            if (i % 6 == 0) {
                course++;
            }
            Classroom classroom = new Classroom();
            classroom.setId(new NanoCombCreator().create().toString());
            classroom.setName("MOCK - " + course);
            classroom.setSubject(subjects.get(i));
            classroom.setAcademicPeriod(savedAcademicPeriod);

            if (teacher >= teachers.size()) {
                teacher = 0;
            }
            classroom.setTeacher(teachers.get(teacher));
            teacher++;
            classrooms.add(
                    classroomRepository.save(classroom)
            );
        }

        List<Student> students = studentRepository.findAll();
        LocalDateTime dateStart = Shared.startDate(request.getDate());

        this.mockRegisters(
                classrooms,
                students,
                dateStart,
                "inscription"
        );

        int countRegister = 0;
        int limit = 0;
        while (dateStart.isBefore(time) && limit < 1000) {

            int actCount = this.mockRegisters(
                    classrooms,
                    students,
                    dateStart,
                    "expired"
            );

            dateStart = dateStart.plusDays(1);
            limit++;

            countRegister += actCount;
        }

        return ResponseEntity.ok(countRegister);
    }

    private int mockRegisters(
            List<Classroom> classrooms,
            List<Student> students,
            LocalDateTime dateStart,
            String status
    ) {
        int count = 0;

        for (Classroom classroom : classrooms) {
            Register register = new Register();
            register.setId(new NanoCombCreator().create().toString());
            register.setStatus(status);
            register.setDate(dateStart);
            register.setClassroom(classroom);

            Register newRegister = registerRepository.save(register);

            for (Student studentSave: students) {
                RegisterStudent registerStudent = new RegisterStudent();
                registerStudent.setId(new NanoCombCreator().create().toString());
                registerStudent.setStatus(register.getStatus());
                registerStudent.setStudent(studentSave);
                registerStudent.setPhoto("");
                registerStudent.setRegister(newRegister);
                registerStudent.setEmotion(
                        CreateStudentRegisterImp.getEmotion()
                );

                registerStudentRepository.save(registerStudent);

                count++;
            }
        }

        return count;
    }
}
