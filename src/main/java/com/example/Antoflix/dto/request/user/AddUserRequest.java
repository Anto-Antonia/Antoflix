package com.example.Antoflix.dto.request.user;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String password;

    private List<String> roleName;
}
