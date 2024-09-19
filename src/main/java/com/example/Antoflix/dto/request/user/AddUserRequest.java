package com.example.Antoflix.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    private String username;
    private String email;
    private String password;
    private List<String> roleName;
}
