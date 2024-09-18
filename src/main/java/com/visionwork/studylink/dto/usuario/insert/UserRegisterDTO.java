package com.visionwork.studylink.dto.usuario.insert;

import jakarta.validation.constraints.*;

public record UserRegisterDTO(
        @NotBlank(message = "Nome de usuário nao pode ser vazio. ")
        @Size(min = 4, max = 20, message = "Nome de usuário deve conter no minimo 3 e no máximo 20 caracteres.")
        String nome_usuario,
        @NotBlank(message = "Email não pode ser vazio.")
        @Email(message = "Email incorreto.")
        String email,
        @NotBlank(message = "Senha não pode ser vazia.")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha) {
}
