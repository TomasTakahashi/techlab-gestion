package com.techlab.ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.techlab.excepciones.NegocioException;
import com.techlab.productos.Producto;
import com.techlab.productos.ProductoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class MenuConsola implements CommandLineRunner {

    private static final Locale LOCALE_AR = Locale.of("es", "AR");

    private static final int OPCION_SALIR = 0;

    private static final String MENU = """

            ===== TechLab - Gestión de productos =====
            1) Agregar producto
            2) Listar productos
            3) Buscar producto por ID
            4) Buscar productos por nombre
            5) Actualizar producto
            6) Eliminar producto
            0) Salir
            """;

    private final EntradaConsola entrada;
    private final ProductoService productoService;

    @Override
    public void run(String... args) {
        int opcion;
        do {
            System.out.print(MENU);
            opcion = entrada.leerEntero("Elegí una opción: ");
            ejecutar(opcion);
        } while (opcion != OPCION_SALIR);
        System.out.println("¡Hasta luego!");
    }

    private void ejecutar(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> listarProductos();
                case 3 -> buscarPorId();
                case 4 -> buscarPorNombre();
                case 5 -> actualizarProducto();
                case 6 -> eliminarProducto();
                case OPCION_SALIR -> {
                }
                default -> System.out.println("Opción inválida. Elegí un número del menú.");
            }
        } catch (NegocioException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("Error inesperado en el menú", e);
            System.out.println("Ocurrió un error inesperado. Intentá de nuevo.");
        }
    }

    private void agregarProducto() {
        String nombre = entrada.leerTexto("Nombre: ");
        BigDecimal precio = entrada.leerPrecio("Precio (ej: 1500,50): ");
        int stock = entrada.leerEntero("Stock: ");
        Producto nuevoProducto = productoService.crear(nombre, precio, stock);
        System.out.println("Producto agregado correctamente con ID " + nuevoProducto.getId() + ".");
    }

    private void listarProductos() {
        List<Producto> productos = productoService.listar();
        mostrarLista(productos);
    }

    private void buscarPorId() {
        long id = entrada.leerId("ID del producto: ");
        Producto producto = productoService.buscarPorId(id);
        System.out.println(formatear(producto));
    }

    private void buscarPorNombre() {
        String nombre = entrada.leerTexto("Nombre del producto: ");
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        mostrarLista(productos);
    }

    private void actualizarProducto() {
        long id = entrada.leerId("ID del producto a actualizar: ");
        Producto producto = productoService.buscarPorId(id);
        System.out.println("Producto encontrado: " + formatear(producto));
        Producto actualizado = switch (entrada.leerEntero("¿Qué querés actualizar? 1) Precio 2) Stock: ")) {
            case 1 -> productoService.actualizarPrecio(id, entrada.leerPrecio("Nuevo precio (ej: 1500,50): "));
            case 2 -> productoService.actualizarStock(id, entrada.leerEntero("Nuevo stock: "));
            default -> throw new IllegalArgumentException("Opción inválida. No se realizaron cambios.");
        };
        System.out.println("Producto actualizado: " + formatear(actualizado));
    }

    private void eliminarProducto() {
        long id = entrada.leerId("ID del producto a eliminar: ");
        Producto producto = productoService.buscarPorId(id);
        System.out.println("Producto encontrado: " + formatear(producto));
        if (entrada.confirmar("¿Seguro desea eliminarlo?")) {
            productoService.eliminar(id);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Operación cancelada. No se realizaron cambios.");
        }
    }

    private String formatear(Producto producto) {
        return String.format(LOCALE_AR, "%s (ID: %d, Precio: $ %,.2f, Stock: %d)",
                producto.getNombre(), producto.getId(), producto.getPrecio(), producto.getStock());
    }

    private void mostrarLista(List<Producto> productos) {
        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos.");
            return;
        }
        System.out.println("Productos encontrados:");
        for (Producto producto : productos) {
            System.out.println("- " + formatear(producto));
        }
    }

}
