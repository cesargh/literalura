package com.github.cesargh.literalura;

import com.github.cesargh.literalura.controller.LibroController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	LibroController libroController;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Transactional(propagation = Propagation.NEVER)
	@Override
	public void run(String... args) throws Exception {

		String titulo = "DEBUG";
		//titulo = "San Francisco";
		//titulo = "Fran";
		//titulo = "San FRANCISCO";
		//titulo = "FRANCIS";
		//titulo = "franç";
		//titulo = "CAÑO";
		//titulo = "año";
		//titulo = "traducción";
		//titulo = "ción";
		//titulo = "cion";
		//titulo = "Frankenstein; Or, The Modern Prometheus";

		System.out.println("DEBUG : Buscador.BuscarLibrosPorTitulo --> INICIO");
		var biblioteca = libroController.BuscarLibrosPorTitulo(titulo);
		if (biblioteca.isEmpty()) {
			System.out.println("DEBUG : Buscador.BuscarLibrosPorTitulo --> EMPTY");
		} else {
			for(Object libro : biblioteca.get()) {
				System.out.printf("DEBUG : Buscador.BuscarLibrosPorTitulo --> %s\n", libro);
			}
		}
		System.out.println("DEBUG : Buscador.BuscarLibrosPorTitulo --> FIN");

	}

}
