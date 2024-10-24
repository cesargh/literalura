package com.github.cesargh.literalura;

import com.github.cesargh.literalura.service.Buscador;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("DEBUG : Inicio de Buscador.BuscarLibrosPorTitulo");
		System.out.println(Buscador.BuscarLibrosPorTitulo("San Francisco"));
		System.out.println("DEBUG : Fin de Buscador.BuscarLibrosPorTitulo");

	}

}
