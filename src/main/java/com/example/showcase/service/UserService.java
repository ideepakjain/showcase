package com.example.showcase.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.showcase.entity.User;
import com.example.showcase.repository.IUserJdbcRepository;

@Service
public class UserService implements IUserService {

	private ReentrantLock lock = new ReentrantLock(true);

	@Autowired
	private IUserJdbcRepository dao;

	@Override
	public Optional<User> getUser(final String username) {
		Optional<List<User>> users = dao.getUsers();
		if (users.isPresent()) {
			return users.get().stream().filter(user -> username.equals(user.getUsername())).findFirst();
		}
		return Optional.empty();
	}

	@Override
	public boolean createUserIfAbsent(User user) {
 		Optional<List<User>> userbyUsername = dao.getUsers();
		if (!userbyUsername.isPresent() || (userbyUsername.isPresent() && !userbyUsername.get().contains(user))) {
		    boolean acquireLock = lock.tryLock();
		    if (!acquireLock) {
 		      return this.createUserIfAbsent(user);
		    }
			try {
				dao.createUser(user);
				return true;
			} finally {
				lock.unlock();
			}
		} else {
			return false;
		}
	}

	@Override
	public int getUserCount() {
		return dao.getAllUserCount();
	}

}
