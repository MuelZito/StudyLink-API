package com.visionwork.studylink.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailRedefinicaoSenha(String email, String token){
        String linkRedefinicao = "http://localhost:8084/api/password-reset/reset?token=" + token;
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email);
        mensagem.setSubject("Redefinição de Senha");
        mensagem.setText("Clique no link abaixo para redefinir sua senha:\n" + linkRedefinicao);
        mailSender.send(mensagem);

    }

}
