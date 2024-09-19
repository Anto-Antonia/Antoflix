package com.example.Antoflix.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleRequest {

    @NotBlank(message = "The role must not be empty")
    private String name;
}
