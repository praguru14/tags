package com.tag.backend.services;

import com.tag.backend.Enum.Roles;
import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.exceptionhandling.InvalidDataException;
import com.tag.backend.model.DataMessage;
import com.tag.backend.repository.LoginRepository;
import com.tag.backend.repository.UsersRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@Builder
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    private final LoginRepository loginRepository;
    @Autowired
    private final UsersRepository usersRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;

    public DataMessage addUser(Login loginModel) {
        if (loginModel == null) {
            throw new InvalidDataException("Please provide user details");
        }
        Roles roles = (loginModel.getRoles() == null) ? Roles.USER : Roles.ADMIN;
        Login loginData =  createLogin(loginModel,roles);
            loginRepository.save(loginData);
            User user = createUserFromLoginModel(loginModel);
            usersRepository.save(user);
            var jwtToken = jwtService.generateToken(loginData);
            System.out.println(jwtToken);
            return new DataMessage(HttpStatus.CREATED, user, "User created successfully", jwtToken);
    }

    public DataMessage getUser(Login login) {
        Optional<User> user = usersRepository.findByEmailAndPhone(login.getEmail(), login.getPhone());
        var jwtToken = jwtService.generateToken(login);
        return new DataMessage(HttpStatus.CREATED, user, "User already exist", jwtToken);
    }

    public DataMessage authenticate(Login loginModel) {
        System.out.println(loginModel);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        var user = loginRepository.findByEmail(loginModel.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new DataMessage(HttpStatus.CREATED, user, "User auth successfully", jwtToken);
    }

    private User createUserFromLoginModel(Login loginModel) {
        User user = new User();
        user.setEmail(loginModel.getEmail());
        user.setPhone(loginModel.getPhone());
        user.setFirstName(loginModel.getFirstName());
        user.setLastName(loginModel.getLastName());
        user.setBloodGroup(loginModel.getBloodGroup());
        return user;
    }

    private Login createLogin(Login loginModel, Roles role) {
        return Login.builder()
                .firstName(loginModel.getFirstName())
                .lastName(loginModel.getLastName())
                .phone(loginModel.getPhone())
                .email(loginModel.getEmail())
                .password(passwordEncoder.encode(loginModel.getPassword()))
                .roles(role)
                .bloodGroup(loginModel.getBloodGroup())
                .build();
    }

    public DataMessage updatePassword(String email, String currentPassword, String newPassword) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, currentPassword));
        Login user = loginRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new DataMessage(HttpStatus.OK, null, "Password updated successfully", jwtToken);
    }


}
