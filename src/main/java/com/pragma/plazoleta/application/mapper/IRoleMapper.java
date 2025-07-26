package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.RoleResponse;
import com.pragma.plazoleta.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IRoleMapper {
    
    Role toRole(String roleName);
    
    RoleResponse toRoleResponse(Role role);
} 