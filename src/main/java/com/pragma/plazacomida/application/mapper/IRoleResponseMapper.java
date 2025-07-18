package com.pragma.plazacomida.application.mapper;

import com.pragma.plazacomida.application.dto.response.RoleResponseDto;
import com.pragma.plazacomida.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleResponseMapper {
    RoleResponseDto toRoleResponseDto(RoleModel roleModel);
} 