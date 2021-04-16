package com.example.showcase.controller;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.showcase.entity.User;
import com.example.showcase.service.IUserService;
import com.example.showcase.util.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1/users")
@Slf4j
public class UserController {

	@Autowired
	private IUserService service;

	@GetMapping("/{username}")
	private ResponseEntity<ApiResponse> getUser(@PathVariable String username) {
		log.debug("Gets the user");
		Optional<User> user = service.getUser(username);
		if (user.isPresent()) {
			return ResponseEntity.ok(new ApiResponse<User>("User Found", Arrays.asList(user.get())));
		} else {
			return ResponseEntity.ok(new ApiResponse<User>("User not found", new ArrayList()));
		}
	}

	@PostMapping()
	private ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
		log.debug("Creates the user");
		boolean userCreated = service.createUserIfAbsent(user);
		ResponseEntity<ApiResponse> response;
		if (userCreated) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Username}")
					.buildAndExpand(user.getUsername()).toUri();
			response = ResponseEntity.created(location).build();
		} else {
			return ResponseEntity.badRequest().build();
		}
		return response;
	}

	@ExceptionHandler
	public ResponseEntity<ApiResponse> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiResponse<User>("Some error occuerd", new ArrayList<User>()));
	}
}
