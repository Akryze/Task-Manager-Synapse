package com.example.Synapse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SynapseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynapseApplication.class, args);
	}

}
