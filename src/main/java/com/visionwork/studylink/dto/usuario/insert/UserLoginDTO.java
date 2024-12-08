package com.visionwork.studylink.dto.usuario.insert;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotBlank(message = "Email não pode ser vazio")
        @Email(message = "Digite um Email válido")
        String email,
        @NotBlank(message = "A senha não pode ser vazia")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
        String senha) {
}
