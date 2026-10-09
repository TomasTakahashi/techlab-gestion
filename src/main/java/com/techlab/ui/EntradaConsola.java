package com.techlab.ui;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class EntradaConsola {

    private final Scanner scanner = new Scanner(System.in,
            Charset.forName(System.getProperty("stdin.encoding"), Charset.defaultCharset()));

    private <T> T leer(String mensaje, Function<String, T> conversor, String mensajeError) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().strip();
            try {
                return conversor.apply(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println(mensajeError);
            }
        }
    }

    public int leerEntero(String mensaje) {
        return leer(mensaje, Integer::parseInt, "Ingresá un número entero válido.");
    }

    public String leerTexto(String mensaje) {
        return leer(mensaje, texto -> {
            if (texto.isBlank()) {
                throw new IllegalArgumentException("Este dato es obligatorio.");
            }
            return texto;
        }, "Este dato es obligatorio.");
    }

    public long leerId(String mensaje) {
        return leer(mensaje, Long::parseLong, "Ingresá un número entero válido.");
    }

    public BigDecimal leerPrecio(String mensaje) {
        return leer(mensaje, texto -> {
            if (texto.contains(".")) {
                throw new IllegalArgumentException();
            }
            return new BigDecimal(texto.replace(',', '.'));
        }, "Precio inválido. Usá coma para los decimales y no uses separador de miles (ej: 1500,50).");
    }

    public boolean confirmar(String mensaje) {
        String respuesta = leer(mensaje + " (s/n): ", texto -> {
            if (!texto.equalsIgnoreCase("s") && !texto.equalsIgnoreCase("n")) {
                throw new IllegalArgumentException();
            }
            return texto;
        }, "Ingresá 's' para sí o 'n' para no.");
        return respuesta.equalsIgnoreCase("s");
    }

}
