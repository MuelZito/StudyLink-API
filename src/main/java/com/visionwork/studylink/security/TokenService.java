package com.visionwork.studylink.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.visionwork.studylink.entities.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String geracaoToke(Usuario usuario){
        try{
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            String token = JWT.create().withIssuer("login-auth-studyLink").withSubject(usuario.getEmail()).withExpiresAt(this.geracaoExepiracaoData()).sign(algoritmo);
            return token;
        }catch (JWTCreationException exeception){
            throw  new RuntimeException("Erro ao autenticar");
        }
    }

    public String validacaoToken(String token){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return (JWT.require(algoritmo).withIssuer("login-auth-studyLink").build().verify(token).getSubject());
        }catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant geracaoExepiracaoData(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
    }

}
