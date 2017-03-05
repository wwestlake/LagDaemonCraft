package com.lagdaemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
public class LagDaemonCraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(LagDaemonCraftApplication.class, args);
	}
}
