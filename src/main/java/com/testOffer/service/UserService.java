package com.testOffer.service;

import java.util.List;

import com.testOffer.entity.User;

public interface UserService {
	
	User saveUser(User user);
	User getDetails(String email) throws Exception ; 
	List<User> getAllUsers();
}
