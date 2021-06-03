package com.testOffer.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.testOffer.controller.UserController;
import com.testOffer.entity.User;
import com.testOffer.repository.UserRepository;
import com.testOffer.service.UserService;
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
	    verify(userRepo,times(1)).save(employee);

	
	
	}
	
	
	@Test
	    public void getDetailsTest()throws Exception{
	        
	        User user = userService.getDetails("oussema@mm.com");
	        verify(userRepo,times(1)).findByEmail("oussema@mm.com");
	    }
}