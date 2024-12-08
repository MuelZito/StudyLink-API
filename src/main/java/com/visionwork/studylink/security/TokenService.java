package com.visionwork.studylink.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.visionwork.studylink.models.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "login-auth-studyLink";


    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new TokenException("Erro ao gerar o token.", exception);
        }
    }


    public String validarToken(String token) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            Instant expiracao = decodedJWT.getExpiresAt().toInstant();
            System.out.println("Hora atual (UTC): " + Instant.now());
            System.out.println("Expiração do token (UTC): " + expiracao);

            if (Instant.now().isAfter(expiracao)) {
                System.out.println("Token expirado. A expiração foi: " + expiracao);
                throw new TokenException("Token expirado.",null);
            }

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println("Erro ao verificar token: " + exception.getMessage());
            throw new TokenException("Token inválido ou expirado.", exception);
        }
    }


    public String gerarTokenRedefinicaoSenha(String email) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            Instant expiracao = Instant.now().plus(15, ChronoUnit.MINUTES); // Correção para usar Instant

            System.out.println("Hora de criação do token (UTC): " + Instant.now());
            System.out.println("Hora de expiração do token (UTC): " + expiracao);
            System.out.println("Hora atual do sistema (UTC): " + Instant.now());

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(email)
                    .withExpiresAt(expiracao) // Usando o Instant diretamente
                    .sign(algoritmo);

        } catch (JWTCreationException exception) {
            throw new TokenException("Erro ao gerar o token de redefinição de senha.", exception);
        }
    }

    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(24)
                .toInstant(ZoneOffset.UTC);
    }

    public static class TokenException extends RuntimeException {
        public TokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
