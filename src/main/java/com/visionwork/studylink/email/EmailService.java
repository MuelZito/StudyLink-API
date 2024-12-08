package com.visionwork.studylink.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailRedefinicaoSenha(String email, String token) {
        String linkRedefinicao = "http://localhost:8084/api/password-reset/reset?token=" + token;

        String corpoHtml = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<div style='max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>" +
                "<h2 style='color: #333;'>Redefinição de Senha</h2>" +
                "<p style='color: #555;'>Olá,</p>" +
                "<p style='color: #555;'>Recebemos uma solicitação para redefinir sua senha. Para continuar, clique no botão abaixo:</p>" +
                "<a href='" + linkRedefinicao + "' " +
                "style='display: inline-block; background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px;'>Redefinir Senha</a>" +
                "<p style='color: #555; margin-top: 20px;'>Se você não solicitou a redefinição de senha, ignore este e-mail.</p>" +
                "<p style='font-size: 12px; color: #777;'>Este e-mail foi enviado automaticamente. Por favor, não responda.</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);
            helper.setTo(email);
            helper.setSubject("Redefinição de Senha");
            helper.setText(corpoHtml, true); // Passa true para indicar que o conteúdo é HTML
            mailSender.send(mensagem);
        } catch (Exception e) {
            e.printStackTrace(); // Trate o erro de forma mais apropriada em um projeto real
        }
    }
}
