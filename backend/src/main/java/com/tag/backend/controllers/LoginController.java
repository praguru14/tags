package com.tag.backend.controllers;

import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.model.DataMessage;
import com.tag.backend.model.UserModel;
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

    @PostMapping(value = "login",produces = MediaType.APPLICATION_JSON_VALUE)
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

//    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<DataMessage> addLoginOrUpdate(@RequestBody Login loginModel) throws Exception {
//        if (loginModel != null) {
//            Optional<Login> existingLogin = loginRepository.findByEmailAndPhone(loginModel.getEmail(), loginModel.getPhone());
//            if (existingLogin.isPresent()) {
//                Optional<User> userExist = usersRepository.findByEmailAndPhone(loginModel.getEmail(), loginModel.getPhone());
//                if (userExist.isPresent()) {
//                    User existingUser = userExist.get();
//                    UserModel userModel = new UserModel();
//                    userModel.setId(existingUser.getId());
//                    userModel.setEmail(existingUser.getEmail());
//                    userModel.setPhone(existingUser.getPhone());
//                    userModel.setFirstName(existingUser.getFirstName());
//                    userModel.setBloodGroup(existingUser.getBloodGroup());
//
//                    // Retrieve access token from DataMessage returned by addUser method
//                    DataMessage addUserResponse = loginService.addUser(loginModel);
//                    String accessToken = addUserResponse.getAccessToken();
//
//                    return new ResponseEntity<>(new DataMessage(HttpStatus.OK, userModel, "User already exists", accessToken), HttpStatus.OK);
//                } else {
//                    return new ResponseEntity<>(new DataMessage(HttpStatus.NOT_FOUND, "User not found"), HttpStatus.NOT_FOUND);
//                }
//            } else {
//                Object newUser = loginService.addUser(loginModel);
//                if (newUser instanceof DataMessage) {
//                    // If the newUser is an instance of DataMessage, retrieve the access token from it
//                    DataMessage addUserResponse = (DataMessage) newUser;
//                    String accessToken = addUserResponse.getAccessToken();
//                    return new ResponseEntity<>(new DataMessage(HttpStatus.CREATED, addUserResponse.getData(), "User created successfully", accessToken), HttpStatus.CREATED);
//                } else {
//                    // Handle the case when the newUser is not an instance of DataMessage
//                    return new ResponseEntity<>(new DataMessage(HttpStatus.CREATED, newUser, "User created successfully", ""), HttpStatus.CREATED);
//                }
//            }
//        }
//        return new ResponseEntity<>(new DataMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Bad request"), HttpStatus.INTERNAL_SERVER_ERROR);
//    }



    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<DataMessage> authenticate(@RequestBody Login loginModel) {
        return ResponseEntity.ok(loginService.authenticate(loginModel));
    }
}


