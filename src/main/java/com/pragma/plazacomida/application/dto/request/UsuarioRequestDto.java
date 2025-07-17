package com.pragma.plazacomida.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede exceder 50 caracteres")
    private String apellido;
    
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no puede exceder 20 caracteres")
    private String numeroDocumento;
    
    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "El celular debe tener entre 10 y 15 dígitos")
    private String celular;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correo;
    
    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 6, max = 100, message = "La clave debe tener entre 6 y 100 caracteres")
    private String clave;
    
    @NotNull(message = "El rol es obligatorio")
    @Min(value = 1, message = "El ID del rol debe ser mayor a 0")
    private Long idRol;
} 