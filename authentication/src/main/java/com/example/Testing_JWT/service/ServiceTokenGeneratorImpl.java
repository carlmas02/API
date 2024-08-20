package com.example.Testing_JWT.service;

import com.example.Testing_JWT.domain.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceTokenGeneratorImpl implements SecurityTokenGenerator {

    @Value("${security-key}")
    private String securityKey;

    Map<String, String> jwtMap = new HashMap<>();

    @Override
    public Map<String, String> generateToken(UserEntity userEntity) {

        String jwtToken;
        jwtToken = Jwts.builder().setSubject(userEntity.getEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, securityKey).compact();
        jwtMap.put("token", jwtToken);
        jwtMap.put("message", "token generated");
        return jwtMap;


    }
}
