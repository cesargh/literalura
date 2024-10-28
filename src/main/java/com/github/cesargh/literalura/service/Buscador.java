package com.github.cesargh.literalura.service;

import com.github.cesargh.literalura.model.DatoBiblioteca;
import com.github.cesargh.literalura.model.DatoLibro;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class Buscador {

    private Buscador() {
    }

    private static String RequerirJSON(String targetURL) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(targetURL)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static List<DatoLibro> BuscarLibrosPorTitulo(String titulo) throws BuscadorException {
        if ((titulo == null) || titulo.isBlank()) {
            throw new BuscadorException(new IllegalArgumentException("Omisión de parámetro"));
        } else {
            try {
                List<DatoLibro> libros = new ArrayList<>();
                String targetURL = "https://gutendex.com/books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                while (targetURL != null) {
                    System.out.printf("DEBUG : Procesando URL %s\n", targetURL);
                    String json = RequerirJSON(targetURL);
                    DatoBiblioteca datoBiblioteca = Conversor.Convertir(json, DatoBiblioteca.class);
                    if (datoBiblioteca.libros().isEmpty()) {
                        targetURL = null;
                    } else {
                        libros.addAll(
                            datoBiblioteca.libros().stream()
                            .filter(x -> x.titulo().toLowerCase().contains(titulo.toLowerCase()))
                            .toList());
                        targetURL = datoBiblioteca.siguienteURL();
                    }
                }
                return libros;
            } catch (Throwable e) {
                throw new BuscadorException(e);
            }
        }
    }

}
