package com.isa.med_hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories
@EntityScan
@SpringBootApplication
public class MedHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedHospitalApplication.class, args);
	}

}
