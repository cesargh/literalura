package com.github.cesargh.literalura.presentation;

import com.github.cesargh.literalura.controller.AutorController;
import com.github.cesargh.literalura.controller.LibroController;

import java.util.*;

public class Gestor {

    //region [Category: Enumeradores]

    enum OpcionMenu {
        BUSCAR_Y_GUARDAR_LIBROS(1, "Buscar y guardar libros"),
        VER_LIBROS(2, "Ver libros"),
        VER_LIBROS_POR_IDIOMA(3,"Ver libros por idioma"),
        VER_CANTIDAD_DE_LIBROS_POR_IDIOMA(4, "Ver cantidad de libros por idioma"),
        VER_TOP10_LIBROS_DESCARGADOS(5, "Ver top 10 libros descargados"),
        VER_AUTORES(6, "Ver autores"),
        VER_AUTORES_VIVOS(7, "Ver autores vivos"),
        VER_TOP10_AUTORES_JOVENES(8,"Ver top 10 autores jóvenes"),
        VER_CANTIDAD_DE_AUTORES_POR_IDIOMA(9,"Ver cantidad de autores por idioma"),
        FINALIZAR(10, "Finalizar");

        private final int numero;
        private final String descripcion;

        OpcionMenu(int numero, String descripcion) {
            this.numero = numero;
            this.descripcion = descripcion;
        }

        public int getNumero() {
            return numero;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static OpcionMenu fromNumero(int numero) {
            for (OpcionMenu opcion : OpcionMenu.values()) {
                if (opcion.numero == numero) {
                    return opcion;
                }
            }
            return null;
        }

        public static OpcionMenu fromValorNumerico(String valorNumerico) {
            try {
                int numero = Integer.parseInt(valorNumerico);
                return fromNumero(numero);
            } catch (NumberFormatException e) {
                return null;
            }
        }

    }

    //endregion [Category: Enumeradores]

    //region [Category: Variables]

    private final LibroController libroController;
    private final AutorController autorController;
    private final Scanner scanner;
    private final Impresor impresor;

    //endregion [Category: Variables]

    //region [Category: Constructores]

    public Gestor(LibroController libroController, AutorController autorController) {
        this.libroController = libroController;
        this.autorController = autorController;
        this.scanner = new Scanner(System.in);
        this.impresor = new Impresor();
    }

    //endregion [Category: Constructores]

    //region [Category: Métodos]

    private int IngresarFiltroAnio(String prompt) {
        int anio = 0;
        while (anio == 0) {
            impresor.Imprimir(prompt);
            impresor.Imprimir("(Año negativo implica \"Antes de Cristo\")");
            impresor.ImprimirPrompt("");
            if (scanner.hasNextLine()) {
                try {
                    anio = Integer.parseInt(scanner.nextLine());
                    if (anio > Calendar.getInstance().get(Calendar.YEAR)) {
                        impresor.ImprimirError("El año no debe ser posterior al año en curso. Reintente!");
                        anio = 0;
                    }
                } catch (Exception e) {
                    impresor.ImprimirError("Opción inválida. Reintente!");
                    anio = 0;
                }
                impresor.Imprimir();
            }
        }
        return anio;
    }

    private String IngresarFiltroIdioma() {
        String texto = "";
        while (texto.isEmpty()) {
            impresor.Imprimir("Ingrese el código de idioma");
            impresor.Imprimir("(Ejemplos: en, es, fr, nl)");
            impresor.ImprimirPrompt("");
            if (scanner.hasNextLine()) {
                texto = scanner.nextLine();
                if (! texto.matches("^[a-zA-Z]{2,3}$")) {
                    impresor.ImprimirError("Opción inválida. Reintente!");
                    texto = "";
                }
                impresor.Imprimir();
            }
        }
        return texto.toLowerCase();
    }

    private String IngresarFiltroTexto(String prompt) {
        String texto = "";
        while (texto.isEmpty()) {
            impresor.Imprimir(prompt);
            impresor.Imprimir("(Puede ser exacto o parcial)");
            impresor.ImprimirPrompt("");
            if (scanner.hasNextLine()) {
                texto = scanner.nextLine();
                if (texto.isBlank()) {
                    impresor.ImprimirError("Opción inválida. Reintente!");
                    texto = "";
                }
                impresor.Imprimir();
            }
        }
        return texto;
    }

    private void Finalizar() {
        impresor.Imprimir();
        impresor.Imprimir("Adios, y recuerda...");
        impresor.ImprimirFrase("Los libros son un antídoto para la ignorancia!");
        impresor.ImprimirFrase("(Terry Pratchett)");
        impresor.Imprimir();
    }

    private void VerMenu() {
        impresor.Imprimir();
        impresor.ImprimirTitulo("Menú de opciones");
        Arrays.stream(OpcionMenu.values())
                .sorted(Comparator.comparingInt(OpcionMenu::getNumero))
                .forEach(opcion -> impresor.ImprimirMenu(opcion.getNumero()," : " + opcion.getDescripcion()));
        impresor.ImprimirPrompt("Ingrese opción");
    }

    public void Ejecutar() {
        try {
            impresor.Imprimir();
            impresor.ImprimirTitulo("LITERALURA");
            boolean continuar = true;
            while (continuar) {
                VerMenu();
                if (scanner.hasNextLine()) {
                    OpcionMenu opcion = OpcionMenu.fromValorNumerico(scanner.nextLine());
                    if (opcion == null) {
                        impresor.ImprimirError("Opción inválida. Reintente!");
                    } else {
                        if (opcion != OpcionMenu.FINALIZAR) {
                            impresor.ImprimirMenu(opcion.getDescripcion());
                        }
                        switch (opcion) {
                            case BUSCAR_Y_GUARDAR_LIBROS:
                                String filtroBYGL = IngresarFiltroTexto("Ingrese el título del libro");
                                impresor.Imprimir("Procesando...");
                                impresor.Imprimir();
                                impresor.ImprimirInformeLibros(libroController.BuscarPorTitulo(filtroBYGL));
                                break;
                            case VER_LIBROS:
                                String filtroVL = IngresarFiltroTexto("Ingrese el título del libro");
                                impresor.ImprimirInformeLibros(libroController.InformarPorTitulo(filtroVL));
                                break;
                            case VER_LIBROS_POR_IDIOMA:
                                String filtroLPI = IngresarFiltroIdioma();
                                impresor.ImprimirInformeLibros(libroController.InformarPorIdioma(filtroLPI));
                                break;
                            case VER_CANTIDAD_DE_LIBROS_POR_IDIOMA:
                                impresor.ImprimirInformeEstadisticas(libroController.InformarCantidadesPorIdioma(), "Cantidad", "Idioma");
                                break;
                            case VER_TOP10_LIBROS_DESCARGADOS:
                                impresor.ImprimirInformeLibros(libroController.ObtenerTop10Descargas());
                                break;
                            case VER_AUTORES:
                                String filtroVA = IngresarFiltroTexto("Ingrese el nombre del autor");
                                impresor.ImprimirInformeAutores(autorController.InformarPorNombre(filtroVA));
                                break;
                            case VER_AUTORES_VIVOS:
                                int filtroVAV = IngresarFiltroAnio("Ingrese el año de vida del autor");
                                impresor.ImprimirInformeAutores(autorController.InformarVivosPorAnio(filtroVAV));
                                break;
                            case VER_TOP10_AUTORES_JOVENES:
                                impresor.ImprimirInformeAutores(autorController.InformarTop10Jovenes());
                                break;
                            case VER_CANTIDAD_DE_AUTORES_POR_IDIOMA:
                                impresor.ImprimirInformeEstadisticas(autorController.InformarCantidadesPorIdioma(), "Cantidad", "Idioma");
                                break;
                            default:
                                Finalizar();
                                continuar = false;
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            impresor.ImprimirError(e);
            throw new RuntimeException(e);
        } finally {
            scanner.close();
        }
    }

    //endregion [Category: Métodos]

}

//endregion [Category: Métodos]
