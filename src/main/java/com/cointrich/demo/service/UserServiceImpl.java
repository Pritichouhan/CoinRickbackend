package com.cointrich.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cointrich.demo.exception.InvalidUserException;
import com.cointrich.demo.exception.UserAlreadyExistsException;
import com.cointrich.demo.model.LoginRequestDto;
import com.cointrich.demo.model.User;
import com.cointrich.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void signUpUser(User user) throws UserAlreadyExistsException, InvalidUserException {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistsException("Username already exists.");
		}
		if (!isValidUsername(user.getUsername())) {
			throw new InvalidUserException("Invalid username.");
		}
		if (!isValidPassword(user.getPassword())) {
			throw new InvalidUserException("Invalid password.");
		}
		userRepository.save(user);
	}

	@Override
	public Optional<User> login(LoginRequestDto loginRequest) throws InvalidUserException {
		Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getPassword().equals(loginRequest.getPassword())) {
				return optionalUser;
			} else {
				throw new InvalidUserException("Invalid password.");
			}
		}
		return optionalUser;

	}

	@Override
	public User getUserProfile(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) {
			throw new RuntimeException("User not found");
		}
		return user.orElse(new User());
	}

	private boolean isValidUsername(String username) {
		String pattern = "^[a-zA-Z0-9]{4,15}$";
		return username.matches(pattern);
	}

	private boolean isValidPassword(String password) {
		String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$";
		return password.matches(pattern);
	}
}
