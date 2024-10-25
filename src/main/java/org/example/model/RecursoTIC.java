package org.example.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

/*
 * Esta clase representa un Recurso TIC
 * Incluye propiedades como código, nombre, tipo y estado.
 * También proporciona métodos para clonar el objeto y utilizar el patrón Builder para la creación de objetos.
 */
public class RecursoTIC implements Cloneable{
    private String codigo;
    private String nombre;
    private String tipo;
    private String estado;

    @Override
    /**
     * Clona el objeto actual.
     * @return Una instancia clonada del objeto
     */
    public RecursoTIC clone(){
        try{
            return (RecursoTIC) super.clone();
        } catch (CloneNotSupportedException e){
            return null;
        }
    }

    // Patrón Builder
    /**
     * Una clase interna estática que nos da una interfaz fluida para crear objetos
     */
    public static class Builder {
        private RecursoTIC recurso;

        /**
         * Inicializa una nueva instancia de Builder.
         */
        public Builder() {
            recurso = new RecursoTIC();
        }

        /**
         * Establece el código del objeto RecursoTic
         * @param codigo El código que se pondrá en el objeto
         * @return La instancia actual de Builder
         */
        public Builder codigo(String codigo) {
            recurso.codigo = codigo;
            return this;
        }

        /**
         * Establece el nombre del objeto
         * @param nombre El nombre que se pondrá en el objeto
         * @return La instancia actual de Builder
         */
        public Builder nombre(String nombre) {
            recurso.nombre = nombre;
            return this;
        }

        /**
         * Establece el tipo del objeto RecursoTIC
         * @param tipo El tipo que se pondrá en el objeto
         * @return La instancia actual de Builder
         */
        public Builder tipo(String tipo) {
            recurso.tipo = tipo;
            return this;
        }

        /**
         * Establece el estado del objeto RecursoTIC
         * @param estado El estado que se pondrá en el objeto
         * @return La instancia actual de Builder
         */
        public Builder estado(String estado) {
            recurso.estado = estado;
            return this;
        }

        /**
         * Construye un objeto RecursoTIC usando los parámetros dados
         * @return Un nuevo objeto RecursoTIC
         * @throws IllegalStateException Si el código o el nombre están vacíos o nulos.
         */
        public RecursoTIC build() {
            // aqui validamos por si el nombre o el codigo estan vacios o nulos
            if (recurso.codigo == null || recurso.codigo.trim().isEmpty()) {
                throw new IllegalStateException("El código no puede estar vacío");
            }
            if (recurso.nombre == null || recurso.nombre.trim().isEmpty()) {
                throw new IllegalStateException("El nombre no puede estar vacío");
            }
            return recurso;
        }
    }
}
