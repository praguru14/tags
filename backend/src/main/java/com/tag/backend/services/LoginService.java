package com.tag.backend.services;

import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.exceptionhandling.InvalidDataException;
import com.tag.backend.model.DataMessage;
import com.tag.backend.repository.LoginRepository;
import com.tag.backend.repository.UsersRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
@Builder
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final UsersRepository usersRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public DataMessage addUser(Login loginModel) {
        if (loginModel == null) {
            throw new InvalidDataException("Please provide user details");
        }
        Login login = Login.builder()
                .firstName(loginModel.getFirstName())
                .lastName(loginModel.getLastName())
                .phone(loginModel.getPhone())
                .email(loginModel.getEmail())
                .password(passwordEncoder.encode(loginModel.getPassword()))
                .roles(loginModel.getRoles())
                .bloodGroup(loginModel.getBloodGroup())
                .build();
        loginRepository.save(login);
        User user = createUserFromLoginModel(loginModel);
        usersRepository.save(user);
        var jwtToken = jwtService.generateToken(login);
        System.out.println(jwtToken);
        return DataMessage.builder().accessToken(jwtToken).build();

    }

public DataMessage authenticate(Login loginModel){
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginModel.getEmail(),
                    loginModel.getPassword()
            )
    );
    var user = loginRepository.findByEmail(loginModel.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return DataMessage.builder().accessToken(jwtToken).build();
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
}
