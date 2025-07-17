package com.pragma.plazacomida.application.mapper;

import com.pragma.plazacomida.application.dto.request.RolRequestDto;
import com.pragma.plazacomida.domain.model.RolModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolRequestMapper {
    RolModel toRolModel(RolRequestDto rolRequestDto);
} 