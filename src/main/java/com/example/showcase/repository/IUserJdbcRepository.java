package com.example.showcase.repository;

import java.util.List;
import java.util.Optional;

import com.example.showcase.entity.User;

public interface IUserJdbcRepository {

	int createUser(User user);

	Integer getAllUserCount();

	Optional<List<User>> getUsers();

}