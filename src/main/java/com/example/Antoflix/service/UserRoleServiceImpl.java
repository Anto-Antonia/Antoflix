package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.request.user.UpdateUserPasswordRequest;
import com.example.Antoflix.dto.request.user.UpdateUserRequest;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.exceptions.role.RoleNotFoundException;
import com.example.Antoflix.exceptions.user.UserAlreadyTakenException;
import com.example.Antoflix.exceptions.user.UserNotFoundException;
import com.example.Antoflix.mapper.MovieGenreMapper;
import com.example.Antoflix.mapper.UserRoleMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.repository.RoleRepository;
import com.example.Antoflix.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMapper userRoleMapper;
    private final MovieRepository movieRepository; // added movieRepo for the relationship between user and movie
    private final MovieGenreMapper movieGenreMapper;

    public UserRoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleMapper userRoleMapper, MovieRepository movieRepository, MovieGenreMapper movieGenreMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleMapper = userRoleMapper;
        this.movieRepository = movieRepository;
        this.movieGenreMapper = movieGenreMapper;
    }

    @Override
    public Role addRole(AddRoleRequest addRoleRequest){
        Role role = userRoleMapper.fromAddRoleRequest(addRoleRequest);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(Integer userId, String roleName) {
//        String userName = addRoleToUserRequest.getUsername();
//        String roleName = addRoleToUserRequest.getRoleName();
//
//        Optional<User> userOptional = userRepository.findUserByUsername(userName);
//        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(roleName);
//
//        if(userOptional.isPresent() && roleOptional.isPresent()){
//            User user = userOptional.get();
//            Role role = roleOptional.get();
//
//            user.addRole(role);
//            userRepository.save(user);
//        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " not found"));
        Role role = roleRepository.findRoleByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role with name: " + roleName + " not found"));

        user.getRoles().clear(); // clearing the old role, making space for the new one
        user.addRole(role);

        userRepository.save(user);
    }

    @Override
    public UserResponse addUser(AddUserRequest addUserRequest) {
        Optional<User> existingUser = userRepository.findUserByEmail(addUserRequest.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyTakenException("Email or username is already in use: " + addUserRequest.getEmail());
        }

       // User user = userRoleMapper.fromAddUserRequest(addUserRequest);
        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setEmail(addUserRequest.getEmail());
        user.setPassword(addUserRequest.getPassword());

        Role role = roleRepository.findRoleByRoleName("user")
                .orElseThrow(()-> new RoleNotFoundException("Role 'user' not found in the DB"));

        user.addRole(role);

        userRepository.save(user);

        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role:: getRoleName).collect(Collectors.toList())
        );

//        String userRole = addUserRequest.getRoleName();
//        List<Role> roles = roleRepository.findAll().stream().filter(element -> userRole.contains(element.getRoleName()))
//                .collect(Collectors.toList());
//        user.setRoles(roles);
//        return userRepository.save(user);
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

    //added logic for adding and removing a movie to a favourite list
    @Override
    public void addMovieToFavourites(Integer userId, Integer movieId){
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new EntityNotFoundException("Movie not found"));

        if(!user.getFavoriteMovie().contains(movie)){
            user.getFavoriteMovie().add(movie);
            userRepository.save(user);
        }
    }

    @Transactional
    @Override
    public void removeMovieFromFavourites(Integer userId, Integer movieId){
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new EntityNotFoundException("Movie not found"));

        if(user.getFavoriteMovie().contains(movie)){
            user.getFavoriteMovie().remove(movie);
            userRepository.save(user);
        }
    }

    @Override
    public List<MovieResponse> getFavouriteMovies(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

        return user.getFavoriteMovie().stream()
                .map(movieGenreMapper::fromMovieResponse)
                .collect(Collectors.toList());
    }
}
