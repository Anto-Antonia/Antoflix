package com.example.Antoflix.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleToUserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String roleName;
}
