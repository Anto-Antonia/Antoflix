package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddRoleToUserRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;

import java.util.List;

public interface UserRoleService {
    public Role addRole(AddRoleRequest addRoleRequest);
    public void addRoleToUser(AddRoleToUserRequest addRoleToUserRequest);
    public User addUser(AddUserRequest addUserRequest);
    void deleteUser(Integer id);
    void deleteRole(Integer id);
    List<UserResponse> getAllUsers();
    public RoleResponse getRole(Integer id);
    public UserResponse getUser(Integer id);



}