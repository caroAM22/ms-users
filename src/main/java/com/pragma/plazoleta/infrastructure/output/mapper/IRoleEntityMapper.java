package com.pragma.plazoleta.infrastructure.output.mapper;

import com.pragma.plazoleta.domain.model.Role;
import com.pragma.plazoleta.infrastructure.output.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    
    Role toRole(RoleEntity roleEntity);
    
    RoleEntity toRoleEntity(Role role);
} 