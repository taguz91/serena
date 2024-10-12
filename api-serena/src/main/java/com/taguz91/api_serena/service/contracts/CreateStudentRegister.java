package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.models.RegisterStudent;
import org.springframework.web.multipart.MultipartFile;

public interface CreateStudentRegister {

    public RegisterStudent create(MultipartFile photo, String idRegister);

    public RegisterStudent duplicate(String idStudent, String idRegister);
}
