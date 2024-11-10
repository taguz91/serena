package com.taguz91.api_serena.service;

import com.taguz91.api_serena.api.exception.ResourceNotFoundException;
import com.taguz91.api_serena.api.request.LoginRequest;
import com.taguz91.api_serena.api.response.SessionInfo;
import com.taguz91.api_serena.models.Classroom;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.ClassroomRepository;
import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.service.contracts.JwtService;
import com.taguz91.api_serena.service.contracts.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Teacher login(LoginRequest request) {
        Teacher teacher = teacherRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(() -> new ResourceNotFoundException("Correo o contrasena incorrectas"));

        boolean isValid = passwordEncoder.matches(
                request.getPassword(),
                teacher.getPassword()
        );

        if (!isValid) {
            throw new ResourceNotFoundException("Correo o contrasena incorrectas");
        }

        // set the new token
        teacher.setToken(jwtService.toToken(teacher));
        teacherRepository.save(teacher);

        return teacher;
    }

    @Override
    public SessionInfo info(Teacher teacher) {
        List<Classroom> classroomList = classroomRepository.findAllByTeacherAcademicPeriodActive(
                teacher.getId()
        );

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setAcademicPeriods(
                classroomList.stream()
                        .map((classroom -> classroom.getAcademicPeriod().getId()))
                        .toList()
        );

        return sessionInfo;
    }
}
