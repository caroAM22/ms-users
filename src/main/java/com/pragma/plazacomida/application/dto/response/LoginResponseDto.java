package com.pragma.plazacomida.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String tipo = "Bearer";
    private UsuarioResponseDto usuario;
} 