package com.cointrich.demo.service;

import java.util.Optional;

import com.cointrich.demo.exception.InvalidUserException;
import com.cointrich.demo.exception.UserAlreadyExistsException;
import com.cointrich.demo.model.LoginRequestDto;
import com.cointrich.demo.model.User;

public interface UserService {
	
	 
	Optional<User> login(LoginRequestDto loginRequest) throws InvalidUserException;
	 

	User getUserProfile(String username);


	void signUpUser(User user) throws UserAlreadyExistsException, InvalidUserException;



}
