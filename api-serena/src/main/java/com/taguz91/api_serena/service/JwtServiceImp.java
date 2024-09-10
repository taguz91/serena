package com.taguz91.api_serena.service;

import com.taguz91.api_serena.models.Teacher;
import com.taguz91.api_serena.service.contracts.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtServiceImp implements JwtService {
    private final String secret;
    private final int sessionTime;

    public JwtServiceImp(@Value("${jwt.secret}") String secret, @Value("${jwt.sessionTime}") int sessionTime) {
        this.secret = secret;
        this.sessionTime = sessionTime;
    }

    @Override
    public String toToken(Teacher teacher) {
//        System.out.println("EL TOKEN EXPIRARA EN: " + expireTimeFromNow().toString());
        return Jwts.builder().setSubject(teacher.getId())
                .setExpiration(expireTimeFromNow())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    @Override
    public Optional<String> getSubFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
            return Optional.ofNullable(claimsJws.getBody().getSubject());
        } catch (Exception e) {
//            System.out.println("ERROR PARSING TOKEN:" + e.getMessage());
            return Optional.empty();
        }
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime * 1000L);
    }
}
