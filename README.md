# spring-boot-mongodb-crud

This project have 3 REST services: one that allows to register a user and the other one that displays the details of a registered user and display all users.     



## Prerequisites 
- Java
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/guides/index.html)
- [Mongo DB](https://docs.mongodb.com/guides/)


## Tools
- Eclipse or IntelliJ 
- Maven (version >= 3.6.0)
- Postman (or any RESTful API testing tool)


<br/>
###  Build and Run with Docker 
install Docker 

docker build -t testOffer-api .

docker-compose up 


###  Build and Run application
_GOTO >_ **~/absolute-path-to-directory/testOffer**  
and try below command in terminal
> **```mvn spring-boot:run```** it will run application as spring boot application

or
> **```mvn clean install```** it will build application and create **jar** file under target directory 

Run jar file from below path with given command
> **```java -jar ~/testOffer/target/testOffer-0.0.1-SNAPSHOT.jar```**

Or
> run main method from `SpringBootMongoDBApplication.java` as spring boot application.  


||
|  ---------    |
| **_Note_** : In `SpringBootMongoDBApplication.java` class we have autowired  user repositories. <br/>If there is no record present in DB for any one of that module class (user), static data is getting inserted in DB from `HelperUtil.java` class when we are starting the app for the first time.| 



### Code Snippets
1. #### Maven Dependencies
    Need to add below dependencies to enable Mongo related config in **pom.xml**.  
    ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
   

    ```
   
   
2. #### Properties file
    Reading Mongo DB related properties from **application.properties** file and configuring Mongo connection factory for mongoDB.  

    **src/main/resources/application.properties**
     ```
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=testOffer-database

spring.data.mongodb.auto-index-creation=false


     ```
   
   
3. #### Model class
    Below are the model classes which we will store in MongoDB and perform CRUD operations.  
    **com.testOffer.entity**  
    ```
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
    ```
   
   
   
4. #### CRUD operation for User

    In **com.testOffer.controoller** UserController.lass, 
    we have exposed 3 endpoints operations
    - GET All Users
    - GET Details by email
    - Post register user
    
    ```
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
    ```
   
    In **com.testOffer.controoller.repository.UserRepository.java**, we are extending `MongoRepository<User, ID>` interface which enables CRUD related methods.
    ```
    public interface UserRepository extends MongoRepository<User, String> {
    }
    ```
   
   In **com.testOffer.controoller.service.impl.UserServiceImp.java**, we are autowiring above interface using `@Autowired` annotation and doing CRUD operation.




    
 
 
    
### API Endpoints

- #### User Operations
    > **GET Mapping** http://localhost:8080/user/users  - Get all User
  
    
      Request Body  
      ```
    {
        "userId": "60b8fc2917be684776a355cc",
        "firstName": "oussema",
        "lastName": "Ibn mtar",
        "birthOfdate": "1992-04-01T00:00:00",
        "email": "oussema.mtarr@atos.fr",
        "country": "FRANCE",
        "age": 29
    },
    {
        "userId": "60b9040a17be684776a355cf",
        "firstName": "Oussema",
        "lastName": "Ibn Mtar",
        "birthOfdate": "1990-04-01T00:00:00",
        "email": "oussema@atos.com",
        "country": null,
        "age": 31
    },
    {
        "userId": "60b945fb3fabb966db19bfa2",
        "firstName": "oussema",
        "lastName": "Ibn mtar",
        "birthOfdate": "1992-04-01T00:00:00",
        "email": "oussema.mtarr@atos.fr",
        "country": "FRANCE",
        "age": 29
    }
      ```

- #### user Get Operations using JPA
    
    > **GET Mapping** http://localhost:8080/user/oussema.mtarr@atos.fr  - Get user by Email
    
    
                                                           
    Request Body  
    ```
 {
    "userId": "60b8fc2917be684776a355cc",
    "firstName": "string",
    "lastName": "string",
    "birthOfdate": "1992-04-01T00:00:00",
    "email": "oussema.mtarr@atos.fr",
    "country": "FRANCE",
    "age": 29
}
    ``` 

- #### user Post Operation

    
    > **POST Mapping** http://localhost:8080/user/register 
                                                           
    Request Body  
   
    ```
 {
    "userId": "60b8fc2917be684776a355cc",
    "firstName": "string",
    "lastName": "string",
    "birthOfdate": "1992-04-01T00:00:00",
    "email": "oussema.mtarr@atos.fr",
    "country": "FRANCE",
    "age": 29
}

    ``` 
