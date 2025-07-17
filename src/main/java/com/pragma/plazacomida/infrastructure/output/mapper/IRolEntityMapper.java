package com.pragma.plazacomida.infrastructure.output.mapper;

import com.pragma.plazacomida.domain.model.RolModel;
import com.pragma.plazacomida.infrastructure.output.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolEntityMapper {
    RolEntity toRolEntity(RolModel rolModel);
    RolModel toRolModel(RolEntity rolEntity);
} 