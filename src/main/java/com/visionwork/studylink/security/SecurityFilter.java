package com.visionwork.studylink.security;

import com.visionwork.studylink.models.usuario.Usuario;
import com.visionwork.studylink.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);
        System.out.println("Path: " + request.getRequestURI());
        System.out.println("Token recebido: " + (token != null ? token.substring(0, 20) + "..." : "null"));

        if (token != null) {
            try {
                var login = tokenService.validarToken(token);
                System.out.println("Login extraído do token: " + login);

                if (login != null) {
                    var usuario = usuarioRepository.findByEmail(login)
                            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Autenticação configurada com sucesso");
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}