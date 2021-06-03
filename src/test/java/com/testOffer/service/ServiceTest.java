package com.testOffer.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.testOffer.entity.User;
import com.testOffer.repository.UserRepository;
import com.testOffer.service.Imp.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepo;

	@Test
	public void testregiter() throws Exception {
		User employee = new User();
		employee.setAge(18);
		employee.setEmail("oussema@mm.com");

		userService.saveUser(employee);
		verify(userRepo, times(1)).save(employee);

	}

	@Test
	public void getDetailsTest() throws Exception {

		User user = userService.getDetails("oussema@mm.com");
		System.out.println("user" + user);
		verify(userRepo, times(1)).findByEmail("oussema@mm.com");
	}
}