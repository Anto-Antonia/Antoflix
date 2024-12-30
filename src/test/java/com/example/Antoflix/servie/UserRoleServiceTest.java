package com.example.Antoflix.servie;

import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.mapper.UserRoleMapper;
import com.example.Antoflix.repository.RoleRepository;
import com.example.Antoflix.repository.UserRepository;
import com.example.Antoflix.service.UserRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRoleMapper userRoleMapper;
    @InjectMocks
    private UserRoleServiceImpl userRoleService;
    private User user;
    private Role role;
    private UserResponse userResponse;
    private RoleResponse roleResponse;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUsername("username");
        user.setEmail("email");
    }

}
