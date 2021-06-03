package com.testOffer.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testOffer.entity.User;
import com.testOffer.repository.UserRepository;
import com.testOffer.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);

	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();

	}

	@Override
	public User getDetails(String email) {
		return userRepository.findByEmail(email);
	}

}
