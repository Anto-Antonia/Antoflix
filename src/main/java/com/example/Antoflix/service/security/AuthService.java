package com.example.Antoflix.service.security;

import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.request.user.SignInRequest;
import com.example.Antoflix.dto.response.user.SignInResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.exceptions.role.RoleNotFoundException;
import com.example.Antoflix.exceptions.user.UserAlreadyTakenException;
import com.example.Antoflix.mapper.UserRoleMapper;
import com.example.Antoflix.repository.RoleRepository;
import com.example.Antoflix.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    public void getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findUserByUsername(email);

        if(userRepository.findUserByEmail(email).isPresent()){
            throw new UserAlreadyTakenException("Email already in use");
        }
    }

    public void registerUser(AddUserRequest addUserRequest){
        getUserByEmail(addUserRequest.getEmail());

        User user = UserRoleMapper.fromAddUserRequest(addUserRequest);
        Optional<Role> optionalRole = roleRepository.findRoleByRoleName(addUserRequest.getRoleName());

        if(optionalRole.isPresent()){
            user.addRole(optionalRole.get());
        }else{
            throw  new RoleNotFoundException("Role with name " + addUserRequest.getRoleName() + " is not in the database");
        }
        userRepository.save(user);
    }

    public SignInResponse signIn(SignInRequest signInRequest){
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return UserRoleMapper.fromUserDetailImpl(userDetails);

    }
}
