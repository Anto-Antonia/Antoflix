package com.example.Antoflix.mapper;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleMapper {
    public User fromAddUserRequest(AddUserRequest addUserRequest) {
        User user = new User();

        user.setUsername(addUserRequest.getUsername());
        user.setEmail(addUserRequest.getEmail());
        user.setPassword(addUserRequest.getPassword());
        user.setRoles(new ArrayList<>());

        return user;
    }

    public Role fromAddRoleRequest(AddRoleRequest addRoleRequest) {
        Role role = new Role();

        role.setRoleName(addRoleRequest.getName());
        role.setUsers(new ArrayList<>());

        return role;
    }

    public UserResponse fromUserResponse(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());

        if (!user.getRoles().isEmpty()) {
            List<Role> roles = user.getRoles();
            List<String> roleNames = roles.stream().map(r -> r.getRoleName()).toList();
            userResponse.setRoleName(roleNames);
        }
        return userResponse;
    }



}