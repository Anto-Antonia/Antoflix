package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddRoleToUserRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.request.user.UpdateUserRequest;
import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/role")
    public ResponseEntity<Void> addRole(@RequestBody AddRoleRequest addRoleRequest){
        userRoleService.addRole(addRoleRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<UserResponse> addUser(@RequestBody AddUserRequest addUserRequest){
        UserResponse response = userRoleService.addUser(addUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/user/addRoleToUser")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> addRoleToUser(@RequestBody @Valid AddRoleToUserRequest addRoleToUserRequest){
        userRoleService.addRoleToUser(addRoleToUserRequest.getUserId(), addRoleToUserRequest.getRoleName());
        return ResponseEntity.ok("Role added successfully to the user with the ID: " + addRoleToUserRequest.getUserId());
    }

    @DeleteMapping("role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id){
        userRoleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userRoleService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = userRoleService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id){
        UserResponse userResponse = userRoleService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Integer id){
        RoleResponse roleResponse = userRoleService.getRole(id);
        return ResponseEntity.status(HttpStatus.OK).body(roleResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody @Valid UpdateUserRequest updateUserRequest){
        userRoleService.updateUser(id, updateUserRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
