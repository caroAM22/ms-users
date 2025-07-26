package com.pragma.plazoleta.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response containing the role ID")
public class RoleResponse {
    
    @Schema(
        description = "Unique identifier (UUID) of the role",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private String id;

    @Schema(
        description = "Name of the role",
        example = "OWNER"
    )
    private String name;

    @Schema(
        description = "Description of the role",
        example = "Restaurant owner"
    )
    private String description;
} 