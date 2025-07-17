package com.pragma.plazacomida.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
    private Long idRol;
    private RolModel rol;
} 