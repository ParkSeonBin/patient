package com.example.patientinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PatientInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientInfoApplication.class, args);
	}

}
