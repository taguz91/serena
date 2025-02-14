package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.api.request.LoginEmailRequest;
import com.taguz91.api_serena.api.request.LoginRequest;
import com.taguz91.api_serena.api.response.SessionInfo;
import com.taguz91.api_serena.models.Teacher;

public interface LoginService {

    public Teacher login(LoginRequest request);

    public Teacher loginByEmail(LoginEmailRequest request);

    public SessionInfo info(Teacher teacher);
}
