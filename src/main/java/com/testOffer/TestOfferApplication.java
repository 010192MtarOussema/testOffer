package com.testOffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.testOffer.entity.User;
import com.testOffer.repository.UserRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan(basePackageClasses = User.class)
@EnableSwagger2
public class TestOfferApplication implements CommandLineRunner {

	private static final Logger LOGGER = LogManager.getLogger(TestOfferApplication.class);

	@Autowired
	private UserRepository us;

	public static void main(String[] args) {
		SpringApplication.run(TestOfferApplication.class, args);
		LOGGER.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);
	}

	@Override
	public void run(String... args) throws Exception {

		us.deleteAll();

	}

}
