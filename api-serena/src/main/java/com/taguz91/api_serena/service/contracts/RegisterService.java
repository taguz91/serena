package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.api.request.RegisterRequest;
import com.taguz91.api_serena.models.Teacher;

public interface RegisterService {

    public Teacher register(RegisterRequest request);
}
