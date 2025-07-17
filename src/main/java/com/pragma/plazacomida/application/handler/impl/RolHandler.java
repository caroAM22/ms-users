package com.pragma.plazacomida.application.handler.impl;

import com.pragma.plazacomida.application.dto.request.RolRequestDto;
import com.pragma.plazacomida.application.dto.response.RolResponseDto;
import com.pragma.plazacomida.application.handler.IRolHandler;
import com.pragma.plazacomida.application.mapper.IRolRequestMapper;
import com.pragma.plazacomida.application.mapper.IRolResponseMapper;
import com.pragma.plazacomida.domain.api.IRolServicePort;
import com.pragma.plazacomida.domain.model.RolModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RolHandler implements IRolHandler {
    
    private final IRolServicePort rolServicePort;
    private final IRolRequestMapper rolRequestMapper;
    private final IRolResponseMapper rolResponseMapper;
    
    @Override
    public RolResponseDto saveRol(RolRequestDto rolRequestDto) {
        RolModel rolModel = rolRequestMapper.toRolModel(rolRequestDto);
        RolModel savedRol = rolServicePort.saveRol(rolModel);
        return rolResponseMapper.toRolResponseDto(savedRol);
    }
    
    @Override
    public List<RolResponseDto> getAllRoles() {
        return rolServicePort.getAllRoles().stream()
                .map(rolResponseMapper::toRolResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public RolResponseDto getRolById(Long id) {
        return rolServicePort.getRolById(id)
                .map(rolResponseMapper::toRolResponseDto)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }
    
    @Override
    public RolResponseDto getRolByNombre(String nombre) {
        return rolServicePort.getRolByNombre(nombre)
                .map(rolResponseMapper::toRolResponseDto)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }
    
    @Override
    public void deleteRolById(Long id) {
        rolServicePort.deleteRolById(id);
    }
} 