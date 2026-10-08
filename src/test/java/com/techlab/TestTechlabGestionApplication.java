package com.techlab;

import org.springframework.boot.SpringApplication;

public class TestTechlabGestionApplication {

	public static void main(String[] args) {
		SpringApplication.from(TechlabGestionApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
