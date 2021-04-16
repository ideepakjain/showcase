package com.example.showcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.showcase.entity.User;
import com.example.showcase.repository.IUserJdbcRepository;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ShowcaseApplication  implements CommandLineRunner {
	
	@Autowired
	private IUserJdbcRepository dao;

	public static void main(String[] args) {
		SpringApplication.run(ShowcaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
 		dao.createUser(new User("isdeepak", "Deepak", "Ja", "aan@aa.com", 96663332));
 	}

}
