package com.example.Antoflix.mapper;

import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserRoleMapper {
        public User fromAddUserRequest(AddUserRequest addUserRequest){
            User user = new User();
            user.setUsername(addUserRequest.getUsername());
            user.setEmail(addUserRequest.getEmail());
            user.setPassword(addUserRequest.getPassword());
            user.setRoles(new ArrayList<>());

            return user;
        }
}
