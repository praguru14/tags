package com.tag.backend.controllers;
import com.tag.backend.model.DataMessage;
import com.tag.backend.model.UserModel;
import com.tag.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@CrossOrigin
public class GenericController
{
	@Autowired
	private UserService userService;

	@GetMapping(value = "/fetch-user-by-id", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataMessage> fetchUserById(@RequestParam long userId) throws Exception {
		return new ResponseEntity<DataMessage>(new DataMessage(HttpStatus.OK, userService.fetchUserById(userId)),
				HttpStatus.OK);
	}

	@PostMapping(value = "add-user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DataMessage> addUpdateUserDetails(@RequestBody UserModel userModel) throws Exception {
		return new ResponseEntity<DataMessage>(new DataMessage(HttpStatus.OK, userService.addUpdateUserDetails(userModel)),
				HttpStatus.OK);
	}
}
