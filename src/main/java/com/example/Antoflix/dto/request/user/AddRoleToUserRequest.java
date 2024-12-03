package com.example.Antoflix.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleToUserRequest {
    @NotNull
    private Integer userId; // changed the logic, from getting the user by username to id

    @NotBlank
    private String roleName;
}
