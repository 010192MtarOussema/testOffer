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
	 * {@code GET . @return . list of users
	 * 
	 * @throws NO_CONTENT .
	 */
	@LogMethod
	@RequestMapping(Constants.GET_ALL_USERS)
	public ResponseEntity<Object> getAllUsers() {
		try {
			LOGGER.info("REST request to list user : {}");

			List<User> userList = userService.getAllUsers();
			if (userList.isEmpty()) {
				LOGGER.info("EMPTY LIST : NO_CONTENT{}");

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				LOGGER.info("LIST OF USERS{}");
				return new ResponseEntity<Object>(userList, HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("BAD_REQUEST: BAD_REQUEST{}", e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * {@code GET . @return . user details
	 * 
	 * @throws NO_FOUNT .
	 */
	@LogMethod
	@GetMapping(Constants.GET_USER_DETAILS)
	public ResponseEntity<Object> getDetailUser(@PathVariable String email) {
		LOGGER.info("REST request to detail user : {}", email);

		User user;
		try {
			user = userService.getDetails(email);
			LOGGER.info("REST request to detail user : {}", user);

			return new ResponseEntity<Object>(user, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("ERROR request to detail user : {}", email);

			return new ResponseEntity<Object>(e.getMessage().toString(), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * {@code POST .
	 *
	 * @param user
	 * 
	 * @return .created
	 * @throws .
	 */
	@RequestMapping(value = Constants.SAVE_USER, method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {

		LOGGER.info("REST request to save user : {}", user);

		if (checkCity(user.getCountry())) {
			if ((getAge(LocalDateTime.now(), user.getBirthOfdate()) > 18)) {
				user.setAge(getAge(LocalDateTime.now(), user.getBirthOfdate()));
				userService.saveUser(user);
				LOGGER.info("Valid condition save user : {}");

				return new ResponseEntity<Object>(user, HttpStatus.CREATED);
			} else {
				LOGGER.error("ERROR request to save user : {}", Constants.AGE_NOT_VALID);

				return new ResponseEntity<Object>(Constants.AGE_NOT_VALID, HttpStatus.NOT_ACCEPTABLE);
			}

		}

		else {
			LOGGER.error("ERRO request to save user : {}", Constants.CITY_NOT_VALID);
			return new ResponseEntity<Object>(Constants.CITY_NOT_VALID, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	/**
	 * {@code Check Pays
	 *
	 * @param String
	 * 
	 * @return .true if pays is FRANCE .
	 */
	private boolean checkCity(String city) {
		if (city.equals("FRANCE")) {
			return true;
		} else {

			return false;
		}

	}

	/**
	 * {@code difference between two date
	 *
	 * @param dateNow and birthDate
	 * 
	 * @return .value is the number between two date .
	 */
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
