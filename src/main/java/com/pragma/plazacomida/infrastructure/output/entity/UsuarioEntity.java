package com.pragma.plazacomida.infrastructure.output.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "apellido", nullable = false)
    private String apellido;
    
    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento;
    
    @Column(name = "celular", nullable = false)
    private String celular;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;
    
    @Column(name = "clave", nullable = false)
    private String clave;
    
    @Column(name = "id_rol", nullable = false)
    private Long idRol;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
    private RolEntity rol;
} 