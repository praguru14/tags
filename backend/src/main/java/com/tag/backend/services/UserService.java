package com.tag.backend.services;

import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import com.tag.backend.exceptionhandling.InvalidDataException;
import com.tag.backend.model.UserModel;
import com.tag.backend.repository.LoginRepository;
import com.tag.backend.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LoginRepository loginRepository;

    public UserModel fetchUserById(long userId) {
        Optional<User> fleetOptional = usersRepository.findById(userId);
        if (fleetOptional.isPresent()) {
            UserModel userModel = new UserModel();
            return fleetOptional.get().convertToModel(userModel);
        } else {
            throw new InvalidDataException("User does not exist");
        }
    }
    public UserModel fetchUserByEmail(String email) {
        Optional<User> fleetOptional = Optional.ofNullable(usersRepository.findByEmail(email));
        if (fleetOptional.isPresent()) {
            UserModel userModel = new UserModel();
            return fleetOptional.get().convertToModel(userModel);
        } else {
            throw new InvalidDataException("User does not exist");
        }
    }

    public User addUpdateUserDetails(UserModel userModel) {
        if (userModel != null) {
            User user = new User();
            Optional<User> userOptional = usersRepository.findById(userModel.getId());
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
            userModel.convertToEntity(user);
            return usersRepository.save(user);
        } else {
            throw new InvalidDataException("Please provide user details");
        }
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public Boolean fetchByEmail(String email) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            return false;
        } else {
            String userEmail = user.getEmail();
            return userEmail.equals(email);
        }
    }

    public String editEmail(String oldEmail,String newEmail) {
        User user = usersRepository.findByEmail(oldEmail);
        Login login = loginRepository.findByEmail(oldEmail).orElseThrow();
        user.setEmail(newEmail);
        login.setEmail(newEmail);
        return "Updated email from" + oldEmail + " to "+ newEmail;
    }

    public void uploadProfilePhoto(Long userId, MultipartFile imageFile) throws IOException {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setImageData(imageFile.getBytes());
        usersRepository.save(user);
    }

}
