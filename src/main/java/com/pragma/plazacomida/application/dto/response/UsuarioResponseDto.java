package com.pragma.plazacomida.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private Long idRol;
    private RolResponseDto rol;
} 