package com.tag.backend.controllers;

import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.model.DataMessage;
import com.tag.backend.repository.LoginRepository;
import com.tag.backend.repository.UsersRepository;
import com.tag.backend.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> addLoginOrUpdate(@RequestBody Login loginModel) throws Exception {

        if (loginModel != null) {
            Optional<Login> existingLogin = loginRepository.findByEmailAndPhone(loginModel.getEmail(), loginModel.getPhone());
            if (existingLogin.isPresent()) {
                Optional<User> userExist = usersRepository.findByEmailAndPhone(loginModel.getEmail(), loginModel.getPhone());
                return new ResponseEntity<>(new DataMessage(HttpStatus.OK, userExist, "User already exists",""), HttpStatus.OK);
            } else {
                Object newUser = loginService.addUser(loginModel);
                return new ResponseEntity<>(new DataMessage(HttpStatus.CREATED, newUser, "User created successfully",newUser.toString()), HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(new DataMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Bad request"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<DataMessage> authenticate(
            @RequestBody Login loginModel
    ) {
        return ResponseEntity.ok(loginService.authenticate(loginModel));
    }
}


