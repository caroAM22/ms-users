package com.pragma.plazacomida.infrastructure.output.mapper;

import com.pragma.plazacomida.domain.model.UserModel;
import com.pragma.plazacomida.infrastructure.output.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {IRoleEntityMapper.class})
public interface IUserEntityMapper {
    UserEntity toUserEntity(UserModel userModel);
    UserModel toUserModel(UserEntity userEntity);
} 