package com.example.wipro;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.example"})

public class WiproApplication {

	public static void main(String[] args) {

		SpringApplication.run(WiproApplication.class, args);

	}

}

