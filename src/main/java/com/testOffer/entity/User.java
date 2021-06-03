package com.testOffer.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "USER_TAB")
public class User{

	@Id 
	private String userId;
	
    @NotEmpty(message = "first name must not be empty")
	private String firstName;
    
    @NotEmpty(message = "last name must not be empty")
	private String lastName;  
	
     
    private LocalDateTime  birthOfdate ;
    
    @NotEmpty(message = "email must not be empty")
    @Email(message = "email should be a valid email")
	private String email ;
    
    @NotEmpty(message = "city must not be empty")
	private String country ;
    
	private long age ;
	
	public User() {
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDateTime getBirthOfdate() {
		return birthOfdate;
	}
	public void setBirthOfdate(LocalDateTime birthOfdate) {
		this.birthOfdate = birthOfdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthOfdate="
				+ birthOfdate + ", email=" + email + ", city=" + country + ", age=" + age + "]";
	} 

	
    
}
