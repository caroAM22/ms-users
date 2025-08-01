package com.pragma.plazoleta.infrastructure.output.jpa.mapper;

import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.infrastructure.output.jpa.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {
    
    User toUser(UserEntity userEntity);
    
    UserEntity toUserEntity(User user);
} 