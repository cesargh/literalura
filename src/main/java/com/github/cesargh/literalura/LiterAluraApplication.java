package com.github.cesargh.literalura;

import com.github.cesargh.literalura.controller.AutorController;
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

	@Autowired
	AutorController autorController;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Transactional(propagation = Propagation.NEVER)
	@Override
	public void run(String... args) throws Exception {

		System.out.println("DEBUG : libroController.BuscarPorTitulo --> INICIO");
		var resultadoBuscarPorTitulo = libroController.BuscarPorTitulo("FrAnCiS");
		if (resultadoBuscarPorTitulo.isEmpty()) {
			System.out.println("DEBUG : libroController.BuscarPorTitulo --> EMPTY");
		} else {
			for(Object obj : resultadoBuscarPorTitulo) {
				System.out.printf("DEBUG : libroController.BuscarPorTitulo --> %s\n", obj);
			}
		}
		System.out.println("DEBUG : libroController.BuscarPorTitulo --> FIN");

		System.out.println("DEBUG : libroController.InformarPorTitulo --> INICIO");
		var resultadoInformarPorTitulo = libroController.InformarPorTitulo("FrAn");
		if (resultadoInformarPorTitulo.isEmpty()) {
			System.out.println("DEBUG : libroController.InformarPorTitulo --> EMPTY");
		} else {
			for(Object obj : resultadoInformarPorTitulo) {
				System.out.printf("DEBUG : libroController.InformarPorTitulo --> %s\n", obj);
			}
		}
		System.out.println("DEBUG : libroController.InformarPorTitulo --> FIN");

		System.out.println("DEBUG : libroController.InformarCantidadesPorIdioma --> INICIO");
		var resultadoInformarCantidadesPorIdioma = libroController.InformarCantidadesPorIdioma();
		if (resultadoInformarCantidadesPorIdioma.isEmpty()) {
			System.out.println("DEBUG : libroController.InformarCantidadesPorIdioma --> EMPTY");
		} else {
			for(Object obj : resultadoInformarCantidadesPorIdioma) {
				System.out.printf("DEBUG : libroController.InformarCantidadesPorIdioma --> %s\n", obj);
			}
		}
		System.out.println("DEBUG : libroController.InformarCantidadesPorIdioma --> FIN");

		System.out.println("DEBUG : autorController.InformarPorNombre --> INICIO");
		var resultadoInformarPorNombre = autorController.InformarPorNombre("rIc");
		if (resultadoInformarPorNombre.isEmpty()) {
			System.out.println("DEBUG : autorController.InformarPorNombre --> EMPTY");
		} else {
			for(Object obj : resultadoInformarPorNombre) {
				System.out.printf("DEBUG : autorController.InformarPorNombre --> %s\n", obj);
			}
		}
		System.out.println("DEBUG : autorController.InformarPorNombre --> FIN");

		System.out.println("DEBUG : autorController.InformarVivosPorAnio --> INICIO");
		var resultadoInformarVivosPorAnio = autorController.InformarVivosPorAnio(1950);
		if (resultadoInformarVivosPorAnio.isEmpty()) {
			System.out.println("DEBUG : autorController.InformarVivosPorAnio --> EMPTY");
		} else {
			for(Object obj : resultadoInformarVivosPorAnio) {
				System.out.printf("DEBUG : autorController.InformarVivosPorAnio --> %s\n", obj);
			}
		}
		System.out.println("DEBUG : autorController.InformarVivosPorAnio --> FIN");

	}

}
