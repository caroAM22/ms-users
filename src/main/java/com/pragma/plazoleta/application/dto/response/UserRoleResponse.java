package com.pragma.plazoleta.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response containing user's role information")
public class UserRoleResponse {
    
    @Schema(description = "Role ID", example = "660e8400-e29b-41d4-a716-446655440001")
    private UUID roleId;
    
    @Schema(description = "Role name", example = "OWNER")
    private String roleName;
} 