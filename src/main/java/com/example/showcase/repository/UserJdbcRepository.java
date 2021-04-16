package com.example.showcase.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.showcase.entity.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserJdbcRepository implements IUserJdbcRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getString("username"), rs.getNString("firstName"), rs.getNString("lastName"),
					rs.getNString("email"), rs.getInt("phone"));
		}

	}

	@Override
 	@Cacheable("users")
	public Optional<List<User>> getUsers() {
		log.debug("Get user is called");
		Optional<List<User>> user = null;
		try {
			List<User> queryForObject = jdbcTemplate.query("select * from user", new UserRowMapper());
			return Optional.of(queryForObject);
		} catch (DataAccessException ex) {
			user = Optional.empty();
		}
		return user;
	}

	@Override
	@CacheEvict(value = "users", allEntries = true) 
	public int createUser(User user) {
		log.debug("Create user is called");
		return jdbcTemplate.update(
				"insert into user (username, firstname, lastname, email, phone) values( ?, ?, ?,?,?)",
				new Object[] { user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(),
						user.getPhone() });
	}

	@Override
	public Integer getAllUserCount() {
		log.debug("Get all users is called");
		return jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
	}

}
