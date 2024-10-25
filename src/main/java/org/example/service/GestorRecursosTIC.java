package org.example.service;

import lombok.*;
import org.example.model.RecursoTIC;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
@ToString

/*
 * Gestor de recursos TIC que permite agregar, listar, actualizar y buscar recursos
 * usa un patrón Singleton para asegurar que solo exista una instancia de la clase.
 */
public class GestorRecursosTIC {

    private static GestorRecursosTIC instance;
    private List<RecursoTIC> recursos;

    /**
     * Obtiene la instancia única del gestor de recursos TIC
     * @return La instancia del gestor de recursos TIC
     */
    public static synchronized GestorRecursosTIC getInstance() {
        if (instance == null) {
            instance = new GestorRecursosTIC();
        }
        return instance;
    }

    /**
     * Constructor privado para evitar la creación de instancias fuera de la clase
     * Inicializa la lista de recursos en el constructor.
     */
    private GestorRecursosTIC() {
        recursos = new ArrayList<>();
    }

    /**
     * Agrega un recurso TIC a la lista de recursos.
     *
     * @param recurso El recurso TIC para añadir
     * @throws IllegalArgumentException Si ya existe un recurso con el mismo código
     */
    public void agregarRecurso(RecursoTIC recurso) {
        if (recursos.stream().anyMatch(r -> r.getCodigo().equals(recurso.getCodigo()))) {
            throw new IllegalArgumentException("Ya existe un recurso con el código " + recurso.getCodigo());
        }
        recursos.add(recurso);
    }

    /**
     * Obtiene una lista de todos los recursos
     *
     * @return Una lista de recursos
     */
    public List<RecursoTIC> listarRecursos() {
        return new ArrayList<>(recursos);
    }

    /**
     * Actualiza un recurso que ya existe en la lista de recursos
     *
     * @param codigo El código del recurso a actualizar.
     * @param nuevoRecurso El recurso TIC con los nuevos datos
     * @return Verdadero si el recurso se actualizó correctamente, falso en caso contrario
     */
    public boolean actualizarRecurso(String codigo, RecursoTIC nuevoRecurso) {
        Optional<RecursoTIC> recursoExistente = recursos.stream()
                .filter(r -> r.getCodigo().equals(codigo))
                .findFirst();

        if (recursoExistente.isPresent()) {
            recursos.remove(recursoExistente.get());
            recursos.add(nuevoRecurso);
            return true;
        }
        return false;
    }

    /**
     * Busca un recurso en la lista de recursos por su código
     *
     * @param codigo El código del recurso a buscar.
     * @return El recurso  encontrado, o null si no se encuentra.
     */
    public RecursoTIC buscarRecurso(String codigo) {
        return recursos.stream()
                .filter(r -> r.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
}