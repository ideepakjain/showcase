package com.example.showcase.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.showcase.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserJob {
	
	@Autowired
	private IUserService service;
	
	@Scheduled(initialDelayString = "${user.count.job.inital.delay}", fixedDelayString = "${user.count.job.fixed.delay}")
	private void getUsersCount() {
		log.info("User count "+service.getUserCount());
	}

}
