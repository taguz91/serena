package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Student;
import org.springframework.web.multipart.MultipartFile;

public interface CreateStudentRegister {

    public RegisterStudent create(MultipartFile photo, String idRegister, Student student);

    public RegisterStudent duplicate(String idStudent, String idRegister);
}
