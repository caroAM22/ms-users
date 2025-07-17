package com.pragma.plazacomida.infrastructure.output.mapper;

import com.pragma.plazacomida.domain.model.UsuarioModel;
import com.pragma.plazacomida.infrastructure.output.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioEntityMapper {
    UsuarioEntity toUsuarioEntity(UsuarioModel usuarioModel);
    UsuarioModel toUsuarioModel(UsuarioEntity usuarioEntity);
} 