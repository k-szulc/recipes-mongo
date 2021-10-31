package xyz.itbs.recipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

//	private static final Logger logger = LoggerFactory.getLogger(RecipesApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(RecipesApplication.class, args);

//		logger.error("Error message !!");
//		logger.info("Info message");

	}

}
