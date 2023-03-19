package com.cointrich.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cointrich.demo.exception.InvalidUserException;
import com.cointrich.demo.exception.UserAlreadyExistsException;
import com.cointrich.demo.model.LoginRequestDto;
import com.cointrich.demo.model.User;
import com.cointrich.demo.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) throws UserAlreadyExistsException, InvalidUserException{
		userService.signUpUser(user);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<Optional<User>> login(@RequestBody LoginRequestDto loginRequest) throws InvalidUserException {
		Optional<User> user = userService.login(loginRequest);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping("/userdetails/{username}")
	public ResponseEntity<User> userdetails(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserProfile(username));
	}

}
