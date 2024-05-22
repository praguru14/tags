package com.tag.backend.controllers;

import com.tag.backend.entity.User;
import com.tag.backend.model.DataMessage;
import com.tag.backend.model.LocationPayload;
import com.tag.backend.model.UserModel;
import com.tag.backend.repository.UsersRepository;
import com.tag.backend.services.UserService;
import com.tag.backend.utils.UtilsFile;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tag")
@CrossOrigin
public class GenericController {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/fetch-user-by-id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> fetchUserById(@RequestParam long userId) throws Exception {
        return new ResponseEntity<DataMessage>(new DataMessage(HttpStatus.OK, userService.fetchUserById(userId)),
                HttpStatus.OK);
    }
    @GetMapping(value = "/fetch-user-by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> fetchUserByEmail(@RequestParam String email) throws Exception {
        return new ResponseEntity<DataMessage>(new DataMessage(HttpStatus.OK, userService.fetchUserByEmail(email)),
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

    @GetMapping(value = "/user-exists-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DataMessage> getUserFromEmail(@RequestParam String email){
        return new ResponseEntity<DataMessage>(new DataMessage(HttpStatus.OK, userService.fetchByEmail(email)), HttpStatus.OK);
    }
    @PostMapping("/edit-email")
    public ResponseEntity<DataMessage> editEmail(@RequestBody String oldEmail,String newEmail){
        return new ResponseEntity<>(new DataMessage(HttpStatus.OK, userService.editEmail(oldEmail, newEmail)), HttpStatus.OK);
    }

    @PostMapping("/{userId}/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(@PathVariable Long userId, @RequestParam("image") MultipartFile imageFile) {
        try {
            userService.uploadProfilePhoto(userId, imageFile);
            return ResponseEntity.ok().body("Profile photo uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile photo");
        }
    }
    @GetMapping("/{userId}/profile-photo")
    public ResponseEntity<byte[]> getProfilePhoto(@PathVariable Long userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        byte[] imageData = user.getImageData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
