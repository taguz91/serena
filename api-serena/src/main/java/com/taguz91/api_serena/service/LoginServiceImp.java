package com.taguz91.api_serena.service;

import com.amazonaws.services.eks.model.NotFoundException;
import com.taguz91.api_serena.api.request.LoginRequest;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.service.contracts.JwtService;
import com.taguz91.api_serena.service.contracts.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Teacher login(LoginRequest request) {
        Teacher teacher = teacherRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(() -> new NotFoundException("Correo o contrasena incorrectas"));


        boolean isValid = passwordEncoder.matches(
                request.getPassword(),
                teacher.getPassword()
        );

        if (!isValid) {
            throw  new NotFoundException("Correo o contrasena incorrectas");
        }

        // set the new token
        teacher.setToken(jwtService.toToken(teacher));
        teacherRepository.save(teacher);

        return teacher;
    }
}
