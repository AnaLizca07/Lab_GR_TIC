package org.example;

import org.example.model.RecursoTIC;
import org.example.service.GestorRecursosTIC;

import java.util.List;
import java.util.Scanner;

/**
 * Esta aplicación implementa un sistema de gestión de recursos tic usando
 * el patrón de diseño Singleton para el gestor, Builder para la creación de recursos y
 * prototipos para la duplicacion de recursos
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Buscar) sobre los recursos TIC.
 *
 * @author Ana Lucelly Lizcano, Juan Esteban Saavedra, Esteban Salazar Mejía
 * @version 1.0
 */

public class Main {
    /**
     * Scanner para lectura de entrada del usuario
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Instancia única del gestor de recursos (Singleton)
     */
    private static final GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

    /**
     * Prototipo base para recursos tipo computadora
     */
    private static final RecursoTIC prototypeComputadora = new RecursoTIC.Builder()
            .codigo("PC-001")
            .nombre("Compu portatil lenovo")
            .tipo("computadora")
            .estado("disponible")
            .build();

    /**
     * Prototipo base para recursos tipo proyector
     */
    private static final RecursoTIC prototypeProyector = new RecursoTIC.Builder()
            .codigo("PY-001")
            .nombre("Proyector LG")
            .tipo("proyector")
            .estado("disponible")
            .build();

    public static void main(String[] args) {
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarRecurso();
                    break;
                case 2:
                    listarRecursos();
                    break;
                case 3:
                    actualizarRecurso();
                    break;
                case 4:
                    buscarRecurso();
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===👩🏽‍💻 GESTIÓN DE RECURSOS TIC 👩🏽‍💻===");
        System.out.println("1. ➕Agregar recurso");
        System.out.println("2. ✅Listar recursos");
        System.out.println("3. 🆕Actualizar recurso");
        System.out.println("4. 🔎Buscar recurso");
        System.out.println("5. 👋🏽Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Gestiona la adición de nuevos recursos
     * Permite seleccionar entre prototipos predefinidos (computadora o proyector)
     * o crear un recurso personalizado.
     *
     * @throws IllegalStateException si hay error en la creación del recurso
     * @throws IllegalArgumentException si los datos ingresados son inválidos
     */
    private static void agregarRecurso() {
        System.out.println("\n=== AGREGAR RECURSO ===");
        System.out.println("Seleccione el tipo de recurso base:");
        System.out.println("1. Computadora");
        System.out.println("2. Proyector");
        System.out.println("3. Otro");

        int tipoBase = scanner.nextInt();
        scanner.nextLine();

        RecursoTIC nuevoRecurso = null;

        // Usar el patrón Prototype
        if (tipoBase == 1) {
            nuevoRecurso = prototypeComputadora.clone();
        } else if (tipoBase == 2) {
            nuevoRecurso = prototypeProyector.clone();
        }

        System.out.print("Ingrese el código: ");
        String codigo = scanner.nextLine();
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        try {
            RecursoTIC recurso = new RecursoTIC.Builder()
                    .codigo(codigo)
                    .nombre(nombre)
                    .tipo(nuevoRecurso != null ? nuevoRecurso.getTipo() : "otro")
                    .estado("disponible")
                    .build();

            gestor.agregarRecurso(recurso);
            System.out.println("Recurso agregado exitosamente");
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Muestra la lista completa de recursos registrados en el sistema.
     * Si no hay recursos, muestra un mensaje indicándolo.
     */
    private static void listarRecursos() {
        System.out.println("\n=== LISTA DE RECURSOS ===");
        List<RecursoTIC> recursos = gestor.listarRecursos();
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos registrados");
        } else {
            recursos.forEach(System.out::println);
        }
    }

    /**
     * Permite actualizar la información de un recurso existente.
     * @throws IllegalStateException si hay error en la actualización
     */
    private static void actualizarRecurso() {
        System.out.println("\n=== ACTUALIZAR RECURSO ===");
        System.out.print("Ingrese el código del recurso a actualizar: ");
        String codigo = scanner.nextLine();

        RecursoTIC recursoExistente = gestor.buscarRecurso(codigo);
        if (recursoExistente == null) {
            System.out.println("Recurso no encontrado");
            return;
        }

        System.out.print("Ingrese el nuevo nombre (Enter para mantener actual): ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = recursoExistente.getNombre();

        System.out.print("Ingrese el nuevo estado (Enter para mantener actual): ");
        String estado = scanner.nextLine();
        if (estado.isEmpty()) estado = recursoExistente.getEstado();

        try {
            RecursoTIC recursoActualizado = new RecursoTIC.Builder()
                    .codigo(codigo)
                    .nombre(nombre)
                    .tipo(recursoExistente.getTipo())
                    .estado(estado)
                    .build();

            if (gestor.actualizarRecurso(codigo, recursoActualizado)) {
                System.out.println("Recurso actualizado exitosamente");
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Busca y muestra la información de un recurso específico por código.
     * Si el recurso no existe, muestra un mensaje indicándolo.
     */
    private static void buscarRecurso() {
        System.out.println("\n=== BUSCAR RECURSO ===");
        System.out.print("Ingrese el código del recurso: ");
        String codigo = scanner.nextLine();

        RecursoTIC recurso = gestor.buscarRecurso(codigo);
        if (recurso != null) {
            System.out.println("Recurso encontrado:");
            System.out.println(recurso);
        } else {
            System.out.println("Recurso no encontrado");
        }
    }
}