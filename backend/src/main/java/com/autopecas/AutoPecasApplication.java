package com.autopecas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoPecasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoPecasApplication.class, args);
		System.out.println("🚀 Sistema de Auto Peças iniciado com sucesso na porta 8081!");
	}

}
