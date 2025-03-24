package com.example.pr1;

import org.springframework.boot.SpringApplication;

public class TestPr1Application {

	public static void main(String[] args) {
		SpringApplication.from(Pr1Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
