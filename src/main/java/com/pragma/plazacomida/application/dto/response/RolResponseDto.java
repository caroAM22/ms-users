package com.pragma.plazacomida.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
} 