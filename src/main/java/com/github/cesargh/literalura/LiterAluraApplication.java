package com.github.cesargh.literalura;

import com.github.cesargh.literalura.controller.AutorController;
import com.github.cesargh.literalura.controller.LibroController;
import com.github.cesargh.literalura.presentation.Gestor;
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

	@Autowired
	AutorController autorController;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Transactional(propagation = Propagation.NEVER)
	@Override
	public void run(String... args) throws Exception {
		try {
			Gestor gestor = new Gestor(libroController,autorController);
			gestor.Ejecutar();
		} catch (Exception e) {
			throw new RuntimeException("Error en LiterAluraApplication.run", e);
		}
	}

}
