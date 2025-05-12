package io.project.dev.athens_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AthensLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AthensLibraryApplication.class, args);
	}

}
