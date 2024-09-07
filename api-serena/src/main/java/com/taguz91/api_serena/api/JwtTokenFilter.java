package com.taguz91.api_serena.api;

import com.taguz91.api_serena.repository.TeacherRepository;
import com.taguz91.api_serena.service.contracts.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TeacherRepository teacherRepository;

//    @Autowired
//    private RequestAttributeSecurityContextRepository requestAttributeSecurityContextRepository;

    @Override
    protected void doFilterInternal(
            @NotNull  HttpServletRequest request,
            @NotNull  HttpServletResponse response,
            @NotNull  FilterChain filterChain
    )  throws ServletException, IOException {

        final String header = "Authorization";
        getTokenString(request.getHeader(header)).flatMap(token -> jwtService.getSubFromToken(token)).ifPresent(id -> {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                teacherRepository.findById(id).ifPresent(teacher -> {
                    System.out.println("The user with token was found: " + teacher.getId());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            teacher,
                            null,
                            Collections.emptyList()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//                    requestAttributeSecurityContextRepository.saveContext(contextHolder, request, response);
                });
            }
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenString(String header) {
        if (header == null) {
            return Optional.empty();
        } else {
            String[] split = header.split(" ");
            if (split.length < 2) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(split[1]);
            }
        }
    }
}
