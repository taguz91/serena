package com.taguz91.api_serena.service;

import com.taguz91.api_serena.api.exception.InvalidRequestException;
import com.taguz91.api_serena.api.request.RegisterRequest;
import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.service.contracts.JwtService;
import com.taguz91.api_serena.service.contracts.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher register(RegisterRequest request) {
        Teacher teacher = request.toTeacher();

        if (teacherRepository.findByEmail(teacher.getEmail()).isPresent()) {
            throw new InvalidRequestException(new Errors() {
                @Override
                public String getObjectName() {
                    return "teacher";
                }

                @Override
                public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

                }

                @Override
                public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

                }

                @Override
                public List<ObjectError> getGlobalErrors() {
                    return List.of();
                }

                @Override
                public List<FieldError> getFieldErrors() {
                    return List.of(new FieldError(
                            "teacher",
                            "email",
                            "El email ya fue utiliza"
                    ));
                }

                @Override
                public Object getFieldValue(String field) {
                    return field;
                }
            });
        }

        teacher.setToken(jwtService.toToken(teacher));
        teacher.setPassword(
                passwordEncoder.encode(teacher.getPassword())
        );

        return teacherRepository.save(teacher);
    }
}
