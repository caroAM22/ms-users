package com.pragma.plazoleta.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to get role ID by name")
public class RoleRequest {
    
    @Schema(
        description = "Name of the role to find",
        example = "ADMIN",
        allowableValues = {"ADMIN", "OWNER", "EMPLOYEE", "CUSTOMER"}
    )
    @NotBlank(message = "Role name is required")
    private String roleName;
} 