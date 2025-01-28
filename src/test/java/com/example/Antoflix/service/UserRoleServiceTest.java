package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.role.AddRoleRequest;
import com.example.Antoflix.dto.request.user.AddUserRequest;
import com.example.Antoflix.dto.request.user.UpdateUserRequest;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.dto.response.role.RoleResponse;
import com.example.Antoflix.dto.response.user.UserResponse;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.entity.Role;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.entity.Watchlist;
import com.example.Antoflix.exceptions.role.RoleNotFoundException;
import com.example.Antoflix.exceptions.user.UserNotFoundException;
import com.example.Antoflix.mapper.MovieGenreMapper;
import com.example.Antoflix.mapper.UserRoleMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.repository.RoleRepository;
import com.example.Antoflix.repository.UserRepository;
import com.example.Antoflix.repository.WatchlistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private WatchlistRepository watchlistRepository;
    @Mock
    private UserRoleMapper userRoleMapper;
    @Mock
    private MovieGenreMapper movieGenreMapper;
    @InjectMocks
    private UserRoleServiceImpl userRoleService;
    private User user;
    private Role role;
    private Movie movie;
    private Watchlist watchlist;

    private WatchlistResponse watchlistResponse;
    private MovieResponse movieResponse;
    private UserResponse userResponse;
    private RoleResponse roleResponse;

    @BeforeEach
    void setup(){
        role = new Role();
        role.setId(1);
        role.setRoleName("user");

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Sample movie");
        movie.setDescription("Description");

        user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setRoles(new ArrayList<>(Arrays.asList(role)));
        user.setFavoriteMovie(new ArrayList<>(Arrays.asList(movie)));
        user.setWatchlists(new ArrayList<>());

        userResponse = new UserResponse();
        userResponse.setUsername("username");
        userResponse.setEmail("email");

        roleResponse = new RoleResponse();
        roleResponse.setName("user");

        movieResponse = new MovieResponse();
        movieResponse.setTitle("Sample movie");
        movieResponse.setDescription("Description");
    }

    @Test
   public void addRole_whenSuccessful_returnRole(){
        AddRoleRequest addRoleRequest = new AddRoleRequest();
        addRoleRequest.setName("user");

        when(userRoleMapper.fromAddRoleRequest(addRoleRequest)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);

        Role result = userRoleService.addRole(addRoleRequest);

        assertEquals("user", result.getRoleName());
        verify(roleRepository, times(1)).save(role);
   }

   @Test
    public void addRoleToUser_whenSuccessful_returnUserWithRole(){
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(roleRepository.findRoleByRoleName("user")).thenReturn(Optional.of(role));

        userRoleService.addRoleToUser(1, "user");

        assertTrue(user.getRoles().contains(role));
        verify(userRepository, times(1)).save(user);
    }
    @Test
    public void addUser_whenSuccessful_returnUser(){
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setUsername("username");
        addUserRequest.setEmail("email");
        addUserRequest.setPassword("password");

        Role userRole = new Role();
        userRole.setId(1);
        userRole.setRoleName("user");

        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setEmail(addUserRequest.getEmail());
        user.setPassword(addUserRequest.getPassword());
        user.addRole(userRole);

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("username");
        userResponse.setEmail("email");
        userResponse.setRoleName(List.of("user"));


        when(userRepository.findUserByEmail(addUserRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.findRoleByRoleName("user")).thenReturn(Optional.of(userRole));

        UserResponse result = userRoleService.addUser(addUserRequest);

        assertNotNull(result);
        assertEquals("username", result.getUsername());
        assertEquals("email", result.getEmail());
        assertEquals(List.of("user"), result.getRoleName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void deleteUser_whenSuccessful_deleteUser(){
        Integer userId = 1;

        userRoleService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void deleteRole_whenSuccessful_deleteRole(){
        Integer roleId = 1;

        userRoleService.deleteRole(roleId);

        verify(roleRepository, times(1)).deleteById(roleId);
    }

    @Test
    public void getAllUsers_whenCalled_returnListOfUsers(){
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userRoleMapper.fromUserResponse(user)).thenReturn(userResponse);

        List<UserResponse> userResponseList = userRoleService.getAllUsers();

        assertEquals(1, userResponseList.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getRoleById_whenRoleDoesExist_throwRoleNotFoundException(){
        int roleId = 1;

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userRoleMapper.fromRoleResponse(role)).thenReturn(roleResponse);

        RoleResponse  response = userRoleService.getRole(roleId);

        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    public void getRoleById_whenRoleDoesNotExist_throwRoleNotFoundException(){
        int roleId = 1;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, ()-> userRoleService.getRole(roleId));

        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    public void getUserById_whenUserExists_returnUser(){
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRoleMapper.fromUserResponse(user)).thenReturn(userResponse);

        UserResponse response = userRoleService.getUserById(userId);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void getUserById_whenUserDoesNotExist_throwUserNotFoundException(){
        int userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userRoleService.getUserById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void updateUser_whenSuccessful_returnUpdatedUser(){
        int userId = 1;

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("Carol");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userRoleService.updateUser(userId, updateUserRequest);

        assertEquals("Carol", user.getUsername());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void addMovieToFavorites_whenSuccessful_addsMovieToUserFavorites(){
        int userId = 1;
        int movieId = 1;

        User user = new User();
        user.setId(userId);
        user.setFavoriteMovie(new ArrayList<>());

        Movie movie = new Movie();
        movie.setId(movieId);

        // mocking the repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(userRepository.save(user)).thenReturn(user);

        // execute the method from service layer
        userRoleService.addMovieToFavourites(userId, movieId);

        // Assertions
        assertTrue(user.getFavoriteMovie().contains(movie));
        verify(userRepository, times(1)).findById(userId);
        verify(movieRepository, times(1)).findById(movieId);
        verify(userRepository, times(1)).save(user);
    }
    @Test
    public void addMovieToFavorite_whenMovieNotFound_throwsMovieNotFoundException(){
        int userId = 1;
        int movieId = 1;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userRoleService.addMovieToFavourites(userId, movieId));

        verify(userRepository, times(1)).findById(userId);
        verify(movieRepository, times(1)).findById(movieId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void removeMovieFromFavorites_whenSuccessful_removesMovieFromUserFavorites(){
        int userId = 1;
        int movieId = 1;

        Movie movie = new Movie();
        movie.setId(movieId);

        User user = new User();
        user.setId(userId);
        user.setFavoriteMovie(new ArrayList<>(List.of(movie)));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(userRepository.save(user)).thenReturn(user);

        userRoleService.removeMovieFromFavourites(userId, movieId);

        assertFalse(user.getFavoriteMovie().contains(movie));
        verify(userRepository, times(1)).findById(userId);
        verify(movieRepository, times(1)).findById(movieId);
        verify(userRepository, times(1)).save(user);
    }
    @Test
    public void getFavouriteMovies_whenSuccessful_returnsListOfMovies() {
        int userId = 1;

        Movie movie1 = new Movie();
        movie1.setId(1);

        Movie movie2 = new Movie();
        movie2.setId(2);

        User user = new User();
        user.setId(userId);
        user.setFavoriteMovie(List.of(movie1, movie2));

        MovieResponse movieResponse1 = new MovieResponse();
        MovieResponse movieResponse2 = new MovieResponse();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieGenreMapper.fromMovieResponse(movie1)).thenReturn(movieResponse1);
        when(movieGenreMapper.fromMovieResponse(movie2)).thenReturn(movieResponse2);

        List<MovieResponse> result = userRoleService.getFavouriteMovies(userId);

        assertEquals(2, result.size());
        assertTrue(result.contains(movieResponse1));
        assertTrue(result.contains(movieResponse2));
        verify(userRepository, times(1)).findById(userId);
        verify(movieGenreMapper, times(2)).fromMovieResponse(any(Movie.class));
    }
}
