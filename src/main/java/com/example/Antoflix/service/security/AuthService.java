package com.example.Antoflix.service.security;

import com.example.Antoflix.dto.request.user.RegisterRequest;
import com.example.Antoflix.dto.request.user.SignInRequest;
import com.example.Antoflix.dto.response.user.RegisterResponse;
import com.example.Antoflix.dto.response.user.SignInResponse;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.exceptions.auth.InvalidPasswordException;
import com.example.Antoflix.exceptions.role.RoleNotFoundException;
import com.example.Antoflix.exceptions.user.UserAlreadyTakenException;
import com.example.Antoflix.mapper.UserRoleMapper;
import com.example.Antoflix.repository.RoleRepository;
import com.example.Antoflix.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder; // dependency injection instead of using BCrypt

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void checkEmailAvailable(String email){
       // Optional<User> userOptional = userRepository.findUserByUsername(email);

        if(userRepository.findUserByEmail(email).isPresent()){
            throw new UserAlreadyTakenException("Email already in use");
        }
    }

    public void validatePassword(String password){
        if(password == null || password.length() < 6){
            throw new InvalidPasswordException("The password must be at least 6 characters long.");
        }
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest){
        checkEmailAvailable(registerRequest.getEmail());

        validatePassword(registerRequest.getPassword());  // validating raw password before encoding it

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        Role defaultRole = roleRepository.findRoleByRoleName("user")
                .orElseThrow(() -> new RoleNotFoundException("Default role 'user' not found"));

        user.addRole(defaultRole);
//        if(optionalRole.isPresent()){
//            user.addRole(optionalRole.get());
//        }else{
//            throw  new RoleNotFoundException("Role with name " + addUserRequest.getRoleName() + " is not in the database");
//        }
        userRepository.save(user);
         return new RegisterResponse(
                 user.getUsername(),
                 user.getEmail(),
                 "User registered successfully!"
         );
    }

    public SignInResponse signIn(SignInRequest signInRequest){
        try {
           // String email = signInRequest.getEmail(),
           // String password = signInRequest.getPassword()
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getEmail(),
                            signInRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findUserByEmail(signInRequest.getEmail())   // fetch user from db
                    .orElseThrow(()-> new BadCredentialsException("User with email not found"));

            UserDetailsImpl userDetails = UserDetailsImpl.build(user); // converting to user detail impl + map response
                    //(UserDetailsImpl) authentication.getPrincipal();

            return UserRoleMapper.fromUserDetailImpl(userDetails);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
