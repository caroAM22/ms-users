package com.pragma.plazacomida.application.mapper;

import com.pragma.plazacomida.application.dto.response.UserResponseDto;
import com.pragma.plazacomida.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {
    UserResponseDto toUserResponseDto(UserModel userModel);
} 