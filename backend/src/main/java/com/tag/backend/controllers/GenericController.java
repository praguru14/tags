package com.tag.backend.controllers;

import com.tag.backend.entity.User;
import com.tag.backend.model.DataMessage;
import com.tag.backend.model.LocationPayload;
import com.tag.backend.model.UserModel;
import com.tag.backend.services.UserService;
import com.tag.backend.utils.UtilsFile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/tag")
@CrossOrigin
public class GenericController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/fetch-user-by-id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> fetchUserById(@RequestParam long userId) throws Exception {
        return new ResponseEntity<DataMessage>(new DataMessage(HttpStatus.OK, userService.fetchUserById(userId)),
                HttpStatus.OK);
    }

    @PostMapping(value = "/add-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> addUpdateUserDetails(@RequestBody UserModel userModel) throws Exception {
        return new ResponseEntity<>(new DataMessage(HttpStatus.OK, userService.addUpdateUserDetails(userModel)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new DataMessage(HttpStatus.OK, users, (long) users.size()));
    }

}
