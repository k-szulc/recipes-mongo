package xyz.itbs.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RecipesApplication {

	public static void main(String[] args) {

//		System.setProperty("os.arch", "x86_64");
		SpringApplication.run(RecipesApplication.class, args);

	}

}
