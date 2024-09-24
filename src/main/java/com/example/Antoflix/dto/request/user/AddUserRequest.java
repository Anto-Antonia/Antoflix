package com.example.Antoflix.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotEmpty
    private String roleName;
}
