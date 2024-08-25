package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.models.Teacher;

import java.util.Optional;

public interface JwtService {
    String toToken(Teacher teacher);

    Optional<String> getSubFromToken(String token);
}
