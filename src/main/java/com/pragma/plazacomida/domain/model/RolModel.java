package com.pragma.plazacomida.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolModel {
    private Long id;
    private String nombre;
    private String descripcion;
} 