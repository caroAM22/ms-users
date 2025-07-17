package com.pragma.plazacomida.application.mapper;

import com.pragma.plazacomida.application.dto.request.UsuarioRequestDto;
import com.pragma.plazacomida.domain.model.UsuarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioRequestMapper {
    UsuarioModel toUsuarioModel(UsuarioRequestDto usuarioRequestDto);
} 