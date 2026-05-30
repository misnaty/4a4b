package com.meows.meows.dto.register;

/* template DTOResponse */
// used as registerResponse

// TODO implement validation

public record RegisterRequest(
        String nome,
        String senha,
        String confirmarSenha
) {
}
