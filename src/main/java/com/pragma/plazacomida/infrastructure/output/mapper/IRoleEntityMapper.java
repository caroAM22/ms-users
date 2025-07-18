package com.pragma.plazacomida.infrastructure.output.mapper;

import com.pragma.plazacomida.domain.model.RoleModel;
import com.pragma.plazacomida.infrastructure.output.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    RoleEntity toRoleEntity(RoleModel roleModel);
    RoleModel toRoleModel(RoleEntity roleEntity);
} 