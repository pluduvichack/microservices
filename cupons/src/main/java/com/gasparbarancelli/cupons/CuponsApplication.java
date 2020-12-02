package com.gasparbarancelli.cupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CuponsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuponsApplication.class, args);
	}

}
