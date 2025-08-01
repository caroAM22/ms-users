package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    
    @Mapping(target = "restaurantId", source = "restaurantId", qualifiedByName = "stringToUuid")
    User toUser(UserRequest userRequest);
    
    @Mapping(target = "restaurantId", source = "restaurantId", qualifiedByName = "uuidToString")
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);
    
    @Named("stringToUuid")
    default UUID stringToUuid(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + value);
        }
    }
    
    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }
} 