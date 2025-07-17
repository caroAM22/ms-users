package com.pragma.plazacomida.application.handler;

import com.pragma.plazacomida.application.dto.request.RolRequestDto;
import com.pragma.plazacomida.application.dto.response.RolResponseDto;

import java.util.List;

public interface IRolHandler {
    RolResponseDto saveRol(RolRequestDto rolRequestDto);
    List<RolResponseDto> getAllRoles();
    RolResponseDto getRolById(Long id);
    RolResponseDto getRolByNombre(String nombre);
    void deleteRolById(Long id);
} 