package com.tag.backend.services;

import com.tag.backend.Enum.Roles;
import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.exceptionhandling.InvalidDataException;
import com.tag.backend.model.DataMessage;
import com.tag.backend.repository.LoginRepository;
import com.tag.backend.repository.UsersRepository;
import com.tag.backend.utils.EmailUtil;
import com.tag.backend.utils.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static java.lang.System.*;

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
    @Autowired
    private final OtpUtil otpUtil;
    @Autowired
    private final EmailUtil emailUtil;

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
            return new DataMessage(HttpStatus.CREATED, user, "User created successfully", jwtToken);
    }

    public DataMessage getUser(Login login) {
        Optional<User> user = usersRepository.findByEmailAndPhone(login.getEmail(), login.getPhone());
        var jwtToken = jwtService.generateToken(login);
        return new DataMessage(HttpStatus.CREATED, user, "User already exist", jwtToken);
    }

    public DataMessage authenticate(Login loginModel) {
        out.println(loginModel);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        var user = loginRepository.findByEmail(loginModel.getEmail()).orElseThrow();
        if(!user.isVerified()){
            return new DataMessage(HttpStatus.CREATED, "Verify yourself");
        }
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
        String otp = otpUtil.generateOtp();
        out.println(otp);
        try{
            emailUtil.sendOtpEmail(loginModel.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send OTP at the moment");
        }
        Login data;
        data = Login.builder()
                .firstName(loginModel.getFirstName())
                .lastName(loginModel.getLastName())
                .phone(loginModel.getPhone())
                .email(loginModel.getEmail())
                .password(passwordEncoder.encode(loginModel.getPassword()))
                .roles(role)
                .bloodGroup(loginModel.getBloodGroup())
                .otp(otp)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
        return data;

    }

    public DataMessage updatePassword(String email, String currentPassword, String newPassword) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, currentPassword));
        Login user = loginRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new DataMessage(HttpStatus.OK, null, "Password updated successfully", jwtToken);
    }


    public String verifyAccount(String email, String otp) {
        Login userByEmail = loginRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found : "+email));
            if(userByEmail.getOtp().equals(otp) && Duration.between(userByEmail.getOtpGeneratedTime(),LocalDateTime.now()).getSeconds()<(10*60)){
                userByEmail.setVerified(true);
                loginRepository.save(userByEmail);
                if(userByEmail.isVerified()){
                  User user = usersRepository.findByEmail(email);
                    user.setIsVerified(true);
                 usersRepository.save(user);

                }
                return "OTP verified";
            }
        return "Please regenerate OTP";
    }


    public String regenerateOtp(String email) {
        Login user = loginRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        loginRepository.save(user);
        return "Email sent... please verify account within 1 minute";
    }

    public String forgotPassword(String email) throws MessagingException {
        loginRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found: "+email));
       emailUtil.resetPassword(email);
       return "Email sent, Kindly check your email";
    }

    public String resetPassword(String email, String newPassword) {
        Login user = loginRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found: "+email));
        user.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(user);
        return "New password set";
    }



}
