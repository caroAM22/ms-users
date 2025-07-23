package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.UserRequest;
import com.pragma.plazoleta.application.dto.response.UserResponse;
import com.pragma.plazoleta.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    
    User toUser(UserRequest userRequest);
    
    UserResponse toUserResponse(User user);
    
    List<UserResponse> toUserResponseList(List<User> users);
} 