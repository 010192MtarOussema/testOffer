package com.testOffer.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testOffer.entity.User;
import com.testOffer.service.UserService;
import com.testOffer.utils.Constants;
import com.testOffer.utils.LogMethod;

@RequestMapping("/user")
@RestController
public class UserController {
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	@Autowired
	UserService userService;
	
	/**
	 * {@code POST .
	 *
	 * @param
	 * 
	 * @return .
	 * @throws .
	 */
	@LogMethod
	@RequestMapping(Constants.GET_ALL_USERS)
	public ResponseEntity<Object> getAllUsers() {
		try {
			List<User> userList = userService.getAllUsers();
			if (userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<Object>(userList, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@LogMethod
	@GetMapping(Constants.GET_USER_DETAILS)
	public ResponseEntity<Object> getDetailUser(@PathVariable String email) {
		User user = userService.getDetails(email) ; 
		
		return new ResponseEntity<Object>(user, HttpStatus.OK);
//		try {
//			List<User> userList = userService.getAllUsers();
//			if (userList.isEmpty()) {
//				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//			} else {
//				return new ResponseEntity<Object>(userList, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}

	}

	/**
	 * {@code POST .
	 *
	 * @param
	 * 
	 * @return .
	 * @throws .
	 */
	@RequestMapping(value = Constants.SAVE_USER, method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {

		LOGGER.info("REST request to save user : {}", user);

		if (checkCity(user.getCity())) {
			if ((getAge(LocalDateTime.now(), user.getBirthOfdate()) > 18)) {
				LOGGER.info("REST request to save user : {}", getAge(LocalDateTime.now(), user.getBirthOfdate()));
				user.setAge(getAge(LocalDateTime.now(), user.getBirthOfdate()));
				return new ResponseEntity<Object>(userService.saveUser(user), HttpStatus.OK);
			} else {
				LOGGER.error("REST request to save user : {}", "age not valid ");

				return new ResponseEntity<Object>("age inférieur 18", HttpStatus.NOT_ACCEPTABLE);
			}

		}

		else {
			LOGGER.error("REST request to save user : {}", "city not france ");

			return new ResponseEntity<Object>("city not france ", HttpStatus.NOT_ACCEPTABLE);
		}

	}

	private boolean checkCity(String city) {
		if (city.equals("FRANCE")) {
			return true;
		}else {
			
			return false ; 
		}
		
	}

	private long getAge(LocalDateTime dateNaow, LocalDateTime birthDate) {
		LocalDateTime joiningDate = LocalDateTime.of(birthDate.getYear(), birthDate.getMonth(),
				birthDate.getDayOfMonth(), birthDate.getHour(), birthDate.getMinute(), birthDate.getSecond());

		LocalDateTime toDateTime = LocalDateTime.now();

		LocalDateTime tempDateTime = LocalDateTime.from(joiningDate);
		long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears(years);

		return years;

	}

}
