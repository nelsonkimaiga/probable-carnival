package com.iFundi.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iFundi.handlers.CustomResponse;
import com.iFundi.handlers.UserResponse;
import com.iFundi.models.User;
import com.iFundi.models.extras.UsersToVerify;
import com.iFundi.repositories.UserRepository;
import com.iFundi.security.AES;
import com.iFundi.services.SendMail;
import com.iFundi.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SendMail sendMail;
	@Autowired
	private UserRepository userRepository;

	public UserController(SendMail sendMail) {
		// TODO Auto-generated constructor stub
		this.sendMail = sendMail;
	}

	//users login
	@PostMapping(value = "/users/login")
	public ResponseEntity<?> authUser(@RequestBody User user) throws Exception {
		String message = "";
		User userpro = userRepository.findByUsernameAndPassword(user.getUsername(), (user.getPassword()));
		System.out.println(userpro);
		if (userpro == null) {
			message = "Unsuccessful Login attempt with invalid credentials: UserName: " + user.getUsername()
					+ " and Password: " + user.getPassword();
			return new ResponseEntity<>(new UserResponse("invalid user credentials", 409, false, UserResponse.APIV),
					HttpStatus.OK);
		} else if (userpro.isLogged_in() == true) {
			return new ResponseEntity<>(new UserResponse("User is already Logged in", 409, false, UserResponse.APIV),
					HttpStatus.OK);
		} else if (userpro != null && userpro.isStatus() != false) {
			System.out.println("email" + user.getEmail());
			userService.SetUserLoggedin(userpro.getId());

			message = "Successful Login to Account with UserName: " + user.getUsername();
			return new ResponseEntity<>(
					new UserResponse("successfully authenticated!", 200, true, UserResponse.APIV, userpro),
					HttpStatus.OK);
		} else {
			message = "Unsuccessful Login attempts with invalid credentials: UserName: " + user.getUsername()
					+ " and Password: " + user.getPassword();
			return new ResponseEntity<>(new UserResponse("invalid user credentials", 409, false, UserResponse.APIV),
					HttpStatus.OK);
		}
	}
	
	//for users reg
	@PostMapping(value = "/users/signup")
	public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
		User username = userService.findByUsername(user.getUsername());
		if (username != null && user.getId() == "0") {
			return new ResponseEntity<>(new UserResponse(UserResponse.APIV, 203, false, "username already exists!"),
					HttpStatus.OK);
		}
		// user.setPassword(AES.encrypt(user.getPassword()));
		User usr = userService.addUser(user);

		if (usr == null) {
			return new ResponseEntity<>(new CustomResponse(UserResponse.APIV, 409, false, "failed to add user"),
					HttpStatus.OK);
		}

		return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV, 201, true, "User records updated"),
				HttpStatus.OK);
	}

	
	//fetching the sytem users
	@GetMapping(value = "/users/all")
	public ResponseEntity getAllUsers() {
		List<User> users = new ArrayList<>();
		userService.getUsers().forEach(user -> {
			try {
				users.add(new User());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return ResponseEntity.status(201).body(users);
	}

	//users change psswd
	@PostMapping(value = "/users/changePassword")
	public ResponseEntity<?> updatePassword(@RequestBody User user) throws Exception {

		int i = userService.updatePassword(user.getUsername(), user.getPassword());

		System.out.println(user.getUsername());
		System.out.print(user.getPassword());

		if (i > 0) {
			return new ResponseEntity<>(
					new CustomResponse(CustomResponse.APIV, 200, true, "Password updated successfully"), HttpStatus.OK);
		} else
			return new ResponseEntity<>(new CustomResponse(CustomResponse.APIV, 201, true, "nothing to be updated"),
					HttpStatus.OK);
	}

}
