package com.aysimasavas.ecommerce.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aysimasavas.ecommerce.userservice.entity.User;
import com.aysimasavas.ecommerce.userservice.header.HeaderGenerator;
import com.aysimasavas.ecommerce.userservice.request.UserRequest;
import com.aysimasavas.ecommerce.userservice.response.AuthResponse;
import com.aysimasavas.ecommerce.userservice.security.JwtTokenProvider;
import com.aysimasavas.ecommerce.userservice.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private HeaderGenerator headerGenerator;

	private AuthenticationManager authenticationManager;

	private JwtTokenProvider jwtTokenProvider;

	private PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody UserRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getUserName(), loginRequest.getPassword());
		Authentication auth = authenticationManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateToken(auth);
		AuthResponse authresponse = new AuthResponse();
		User user = userService.getUserByName(loginRequest.getUserName());
		authresponse.setMessage("Bearer: " + jwtToken);
		authresponse.setUserId(user.getId());
		return authresponse;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest registerRequest) {
		if (userService.getUserByName(registerRequest.getUserName()) != null) {
			return new ResponseEntity<>("username var", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUserName(registerRequest.getUserName());
		user.setUserPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userService.saveUser(user);

		return new ResponseEntity<>("BAŞARILI", HttpStatus.CREATED);
	}

	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "aysima";
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (!users.isEmpty()) {
			return new ResponseEntity<List<User>>(users, headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<User>>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return new ResponseEntity<User>(user, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
		}
		return new ResponseEntity<User>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
	}

}
