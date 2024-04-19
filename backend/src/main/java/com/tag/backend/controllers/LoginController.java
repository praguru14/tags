package com.tag.backend.controllers;

import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.model.DataMessage;
import com.tag.backend.model.UserModel;
import com.tag.backend.repository.LoginRepository;
import com.tag.backend.repository.UsersRepository;
import com.tag.backend.services.LoginService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UsersRepository usersRepository;


    @GetMapping("/")
    public String Hello() {
        return "Hello from Tags team";
    }

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> login(@RequestBody Login login){
        if(login!=null){
            Optional<Login>  existingLogin = loginRepository.findByEmailAndPhone(login.getEmail(), login.getPhone());
        if(existingLogin.isPresent()) {
            Optional<User> userOptional = usersRepository.findByEmailAndPhone(login.getEmail(),login.getPhone());
            if(userOptional.isPresent()){
               DataMessage userExist = loginService.getUser(login);
                return new ResponseEntity<>(new DataMessage(HttpStatus.ACCEPTED, userExist.getData(), "User already exists",userExist.getAccessToken()),HttpStatus.OK);
            }
        }
        DataMessage newUser = loginService.addUser(login);
        String accessToken = newUser.getAccessToken();
        return new ResponseEntity<>(new DataMessage(HttpStatus.ACCEPTED, newUser.getData(), "User created",accessToken),HttpStatus.OK);
        }
        return null;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> authenticate(@RequestBody Login loginModel) throws MessagingException {
        return ResponseEntity.ok(loginService.authenticate(loginModel));
    }

    @PostMapping("/verify-account")
    public  ResponseEntity<String> verifyAccount(@RequestParam String email,@RequestParam String otp){
        return new ResponseEntity<>(loginService.verifyAccount(email,otp),HttpStatus.OK);
    }

    @PostMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
        return new ResponseEntity<>(loginService.regenerateOtp(email), HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MessagingException {
        return new ResponseEntity<>(loginService.forgotPassword(email),HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,@RequestHeader String newPassword) throws MessagingException {
        return new ResponseEntity<>(loginService.resetPassword(email,newPassword),HttpStatus.OK);
    }
}


