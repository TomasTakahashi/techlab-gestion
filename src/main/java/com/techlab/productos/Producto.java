package com.techlab.productos;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "productos")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Producto {

    private static final int LARGO_MAXIMO_NOMBRE = 100;
    private static final int DECIMALES_PRECIO = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private int stock;

    public Producto(String nombre, BigDecimal precio, int stock) {
        this.nombre = validarNombre(nombre);
        this.precio = validarPrecio(precio);
        this.stock = validarStock(stock);
    }

    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = validarPrecio(precio);
    }

    public void setStock(int stock) {
        this.stock = validarStock(stock);
    }

    private static String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        String nombreNormalizado = nombre.strip();
        if (nombreNormalizado.length() > LARGO_MAXIMO_NOMBRE) {
            throw new IllegalArgumentException(
                    "El nombre no puede tener más de " + LARGO_MAXIMO_NOMBRE + " caracteres");
        }
        return nombreNormalizado;
    }

    private static BigDecimal validarPrecio(BigDecimal precio) {
        if (precio == null) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (precio.stripTrailingZeros().scale() > DECIMALES_PRECIO) {
            throw new IllegalArgumentException("El precio no puede tener más de " + DECIMALES_PRECIO + " decimales");
        }
        return precio.setScale(DECIMALES_PRECIO);
    }

    private static int validarStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        return stock;
    }

}
