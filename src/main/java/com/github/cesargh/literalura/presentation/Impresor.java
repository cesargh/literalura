package com.github.cesargh.literalura.presentation;

import com.github.cesargh.literalura.dto.AutorDTO;
import com.github.cesargh.literalura.dto.EstadisticaDTO;
import com.github.cesargh.literalura.dto.IdiomaDTO;
import com.github.cesargh.literalura.dto.LibroDTO;

import java.util.List;

// La omisión del modificador de acceso implica Package-Private.
final class Impresor {

    //region [Category: Enumeradores]

    public enum ColorTag {

        RESET("\033[0m"),

        // Regular Colors
        BLACK("\033[0;30m"),
        RED("\033[0;31m"),
        GREEN("\033[0;32m"),
        YELLOW("\033[0;33m"),
        BLUE("\033[0;34m"),
        PURPLE("\033[0;35m"),
        CYAN("\033[0;36m"),
        WHITE("\033[0;37m"),

        // Bold
        BLACK_BOLD("\033[1;30m"),
        RED_BOLD("\033[1;31m"),
        GREEN_BOLD("\033[1;32m"),
        YELLOW_BOLD("\033[1;33m"),
        BLUE_BOLD("\033[1;34m"),
        PURPLE_BOLD("\033[1;35m"),
        CYAN_BOLD("\033[1;36m"),
        WHITE_BOLD("\033[1;37m"),

        // Underline
        BLACK_UNDERLINED("\033[4;30m"),
        RED_UNDERLINED("\033[4;31m"),
        GREEN_UNDERLINED("\033[4;32m"),
        YELLOW_UNDERLINED("\033[4;33m"),
        BLUE_UNDERLINED("\033[4;34m"),
        PURPLE_UNDERLINED("\033[4;35m"),
        CYAN_UNDERLINED("\033[4;36m"),
        WHITE_UNDERLINED("\033[4;37m"),

        // Background
        BLACK_BACKGROUND("\033[40m"),
        RED_BACKGROUND("\033[41m"),
        GREEN_BACKGROUND("\033[42m"),
        YELLOW_BACKGROUND("\033[43m"),
        BLUE_BACKGROUND("\033[44m"),
        PURPLE_BACKGROUND("\033[45m"),
        CYAN_BACKGROUND("\033[46m"),
        WHITE_BACKGROUND("\033[47m"),

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"),
        RED_BRIGHT("\033[0;91m"),
        GREEN_BRIGHT("\033[0;92m"),
        YELLOW_BRIGHT("\033[0;93m"),
        BLUE_BRIGHT("\033[0;94m"),
        PURPLE_BRIGHT("\033[0;95m"),
        CYAN_BRIGHT("\033[0;96m"),
        WHITE_BRIGHT("\033[0;97m"),

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"),
        RED_BOLD_BRIGHT("\033[1;91m"),
        GREEN_BOLD_BRIGHT("\033[1;92m"),
        YELLOW_BOLD_BRIGHT("\033[1;93m"),
        BLUE_BOLD_BRIGHT("\033[1;94m"),
        PURPLE_BOLD_BRIGHT("\033[1;95m"),
        CYAN_BOLD_BRIGHT("\033[1;96m"),
        WHITE_BOLD_BRIGHT("\033[1;97m"),

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),
        RED_BACKGROUND_BRIGHT("\033[0;101m"),
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),
        PURPLE_BACKGROUND_BRIGHT("\033[0;105m"),
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),
        WHITE_BACKGROUND_BRIGHT("\033[0;107m");

        public final String tag;

        ColorTag(String tag) {
            this.tag = tag;
        }

    }

    //endregion [Category: Colores]

    //region [Category: Variables]

    private final ColorTag colorDefault = ColorTag.BLUE;
    private final ColorTag colorError = ColorTag.RED;
    private final ColorTag colorFrase = ColorTag.YELLOW;
    private final ColorTag colorInforme = ColorTag.WHITE_BRIGHT;
    private final ColorTag colorInformeEtiqueta = ColorTag.WHITE;
    private final ColorTag colorMenu = ColorTag.PURPLE;
    private final ColorTag colorTitulo = ColorTag.BLUE_UNDERLINED;

    //endregion [Category: Variables]

    //region [Category: Constructores]

    public Impresor() {
    }

    //endregion [Category: Constructores]

    //region [Category: Métodos]

    public void Imprimir() {
        System.out.println();
    }

    public void Imprimir(String string) {
        System.out.print(colorDefault.tag + string + ColorTag.RESET.tag + "\n");
    }

    public void ImprimirError(String string) {
        System.out.print(colorError.tag + string + ColorTag.RESET.tag + "\n");
    }

    public void ImprimirError(Exception e) {
        ImprimirError(e.getMessage());
    }

    public void ImprimirFrase(String string) {
        System.out.print(colorFrase.tag + string + ColorTag.RESET.tag + "\n");
    }

    public void ImprimirInforme(String string) {
        ImprimirInforme(true, string);
    }

    public void ImprimirInforme(boolean nextLine, String string) {
        System.out.print(colorInforme.tag + string + ColorTag.RESET.tag + (nextLine ? "\n" : ""));
    }

    public void ImprimirInforme(String etiqueta, Object dato) {
        System.out.print(colorInformeEtiqueta.tag + etiqueta + colorInforme.tag + (dato == null ? "n/a" : dato) + ColorTag.RESET.tag + "\n");
    }

    public void ImprimirInforme(String columnaIzquierda, String separador, String columnaDerecha) {
        System.out.print(colorInformeEtiqueta.tag + columnaIzquierda + separador + columnaDerecha + ColorTag.RESET.tag + "\n");
    }

    public void ImprimirInformeAutores(List<AutorDTO> autores) {
        if ((autores == null) || autores.isEmpty()) {
            ImprimirInforme("No hay autores.");
        } else {
            for (AutorDTO autor : autores) {
                ImprimirInforme("Nombre ...........: ", autor.nombre());
                ImprimirInforme("Año de Nacimiento : ", autor.anioNacimiento());
                ImprimirInforme("Año de Muerte ....: ", autor.anioMuerte());

                String etiquetaLibro = "Libro ............: ";
                if (autor.libros().isEmpty()) {
                    ImprimirInforme(etiquetaLibro, "n/a");
                }
                else {
                    if (autor.libros().size() > 1) etiquetaLibro = "Libros ...........: ";
                    StringBuilder listaLibros = new StringBuilder();
                    for (LibroDTO libro : autor.libros()) {
                        listaLibros.append("                    ");
                        listaLibros.append(libro.titulo());
                        listaLibros.append(" (Gutendex Id : ");
                        listaLibros.append(libro.id());
                        listaLibros.append(")");
                        listaLibros.append("\n");
                    }
                    ImprimirInforme(etiquetaLibro, listaLibros.toString().trim());
                }

                ImprimirInforme("");
            }
        }
    }

    public void ImprimirInformeEstadisticas(List<EstadisticaDTO> estadisticas, String columnaValor, String columnaDescripcion) {
        if ((estadisticas == null) || estadisticas.isEmpty()) {
            ImprimirInforme("No hay estadísticas.");
        } else {
            int anchoVal = columnaValor.length();
            int anchoDes = columnaDescripcion.length();
            ImprimirInforme(columnaValor," ", columnaDescripcion);
            for (EstadisticaDTO est : estadisticas) {
                ImprimirInforme(false, String.format("%" + anchoVal + "s %-" + anchoDes + "s%n", est.valor(), est.descripcion()));
            }
            ImprimirInforme("");
        }
    }

    public void ImprimirInformeLibros(List<LibroDTO> libros) {
        if ((libros == null) || libros.isEmpty()) {
            ImprimirInforme("No hay libros.");
        } else {
            for (LibroDTO libro : libros) {
                ImprimirInforme("Gutendex Id : ", libro.id());
                ImprimirInforme("Título .....: ", libro.titulo());
                ImprimirInforme("Descargas ..: ", libro.descargas());

                String etiquetaIdioma = "Idioma .....: ";
                if (libro.idiomas().isEmpty()) {
                    ImprimirInforme(etiquetaIdioma , "n/a");
                } else {
                    if (libro.idiomas().size() > 1) etiquetaIdioma = "Idiomas ....: ";
                    StringBuilder listaIdiomas = new StringBuilder();
                    for (IdiomaDTO idioma : libro.idiomas()) {
                        listaIdiomas.append("[").append(idioma.codigo()).append("] ");
                    }
                    ImprimirInforme(etiquetaIdioma , listaIdiomas.toString().trim());
                }

                String etiquetaAutor = "Autor ......: ";
                if (libro.autores().isEmpty()) {
                    ImprimirInforme(etiquetaAutor, "n/a");
                }
                else {
                    if (libro.autores().size() > 1) etiquetaAutor = "Autores ....: ";
                    StringBuilder listaAutores = new StringBuilder();
                    for (AutorDTO autor : libro.autores()) {
                        listaAutores.append("              ");
                        listaAutores.append(autor.nombre());
                        listaAutores.append("\n");
                    }
                    ImprimirInforme(etiquetaAutor, listaAutores.toString().trim());
                }

                ImprimirInforme("");
            }
        }
    }

    public void ImprimirMenu(int opcion, String descripcion) {
        System.out.print(colorMenu.tag + String.format("%2d", opcion) + colorDefault.tag + descripcion + ColorTag.RESET.tag + "\n");
    }

    public void ImprimirMenu(String titulo) {
        Imprimir();
        ImprimirTitulo(titulo);
        Imprimir();
    }

    public void ImprimirPrompt(String string) {
        ImprimirPrompt(false, string);
    }

    public void ImprimirPrompt(boolean nextLine, String string) {
        System.out.print(colorDefault.tag + string + colorMenu.tag + (nextLine ? "\n" : "") + " ---> " + ColorTag.RESET.tag);
    }

    public void ImprimirTitulo(String string) {
        System.out.print(colorTitulo.tag + string + ColorTag.RESET.tag + "\n");
    }

    //endregion [Category: Métodos]

}
