package com.tag.backend.services;

import com.tag.backend.entity.User;
import com.tag.backend.exceptionhandling.InvalidDataException;
import com.tag.backend.model.UserModel;
import com.tag.backend.repository.UsersRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UsersRepository usersRepository;

    public UserModel fetchUserById(long userId) {
        Optional<User> fleetOptional = usersRepository.findById(userId);
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

}
