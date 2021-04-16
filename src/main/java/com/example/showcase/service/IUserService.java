package com.example.showcase.service;

import java.util.Optional;

import com.example.showcase.entity.User;

public interface IUserService {

	Optional<User> getUser(String username);

	boolean createUserIfAbsent(User user);

	int getUserCount();

}