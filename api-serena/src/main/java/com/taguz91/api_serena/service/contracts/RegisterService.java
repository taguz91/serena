package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.api.request.RegisterTeacherRequest;
import com.taguz91.api_serena.models.Teacher;

public interface RegisterService {

    public Teacher register(RegisterTeacherRequest request);
}
