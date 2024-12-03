package com.example.Antoflix.mapper;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.SignInResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.service.security.UserDetailsImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleMapper {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User fromAddUserRequest(AddUserRequest addUserRequest) {
        User user = new User();

        user.setUsername(addUserRequest.getUsername());
        user.setEmail(addUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(addUserRequest.getPassword()));
        user.setRoles(new ArrayList<>());

        return user;
    }

    public static Role fromAddRoleRequest(AddRoleRequest addRoleRequest) {
        Role role = new Role();

        role.setRoleName(addRoleRequest.getName());
        role.setUsers(new ArrayList<>());

        return role;
    }

    public static UserResponse fromUserResponse(User user) {
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

    public static RoleResponse fromRoleResponse(Role role){
        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setName(role.getRoleName());
        return roleResponse;
    }

   public static SignInResponse fromUserDetailImpl(UserDetailsImpl userDetailsImpl){
        SignInResponse signInResponse = new SignInResponse();

        signInResponse.setUsername(userDetailsImpl.getUsername());
        signInResponse.setEmail(userDetailsImpl.getEmail());
        List<String> roles = userDetailsImpl.getAuthorities().stream().map(a-> a.getAuthority()).toList();
        signInResponse.setRoleName(roles);

        return signInResponse;
   }

}