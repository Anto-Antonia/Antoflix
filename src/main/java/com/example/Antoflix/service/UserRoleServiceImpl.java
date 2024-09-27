package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddRoleToUserRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.request.user.UpdateUserRequest;
import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.exceptions.role.RoleNotFoundException;
import com.example.Antoflix.exceptions.user.UserNotFoundException;
import com.example.Antoflix.mapper.UserRoleMapper;
import com.example.Antoflix.repository.RoleRepository;
import com.example.Antoflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleMapper userRoleMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Role addRole(AddRoleRequest addRoleRequest){
        Role role = userRoleMapper.fromAddRoleRequest(addRoleRequest);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(AddRoleToUserRequest addRoleToUserRequest) {
        String userName = addRoleToUserRequest.getUsername();
        String roleName = addRoleToUserRequest.getRoleName();

        Optional<User> userOptional = userRepository.findUserByUsername(userName);
        Optional<Role> roleOptional = roleRepository.findRoleByName(roleName);

        if(userOptional.isPresent() && roleOptional.isPresent()){
            User user = userOptional.get();
            Role role = roleOptional.get();

            user.addRole(role);
            userRepository.save(user);
        }
    }

    @Override
    public User addUser(AddUserRequest addUserRequest) {
        User user = userRoleMapper.fromAddUserRequest(addUserRequest);

        List<String> userRole = addUserRequest.getRoleName();
        List<Role> roles = roleRepository.findAll().stream().filter(element -> userRole.contains(element.getRoleName()))
                .collect(Collectors.toList());

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponseList = users.stream()
                .map(element -> userRoleMapper.fromUserResponse(element)).collect(Collectors.toList());
        return userResponseList;
    }

    @Override
    public RoleResponse getRole(Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if(roleOptional.isPresent()){
            Role role = roleOptional.get();
            RoleResponse roleResponse = userRoleMapper.fromRoleResponse(role);

            return roleResponse;
        } else{
            throw new RoleNotFoundException("Role not found");
        }
    }

    @Override
    public UserResponse getUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            UserResponse userResponse = userRoleMapper.fromUserResponse(user);

            return userResponse;
        } else{
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void updateUser(Integer id, UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();
            userToUpdate.setUsername(updateUserRequest.getUsername());
            userRepository.save(userToUpdate);
        } else {
            throw new UserNotFoundException("The user with id " + id + "does not exist");
        }
    }
}
