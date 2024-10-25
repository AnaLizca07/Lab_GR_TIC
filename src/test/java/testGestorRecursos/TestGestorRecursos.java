package testGestorRecursos;

import org.example.model.RecursoTIC;
import org.example.service.GestorRecursosTIC;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestGestorRecursos {
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarRecursoConCodigoExistente() {
        GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

        RecursoTIC recurso1 = new RecursoTIC.Builder()
                .codigo("R001")
                .nombre("recurso 1")
                .estado("disponible")
                .build();
        RecursoTIC recurso2 = new RecursoTIC.Builder()
                .codigo("R001")
                .nombre("recurso 2")
                .estado("disponible")
                .build();

        gestor.agregarRecurso(recurso1);
        gestor.agregarRecurso(recurso2); // Deberia de lanzar un illegal argument exception
    }
    
    @Test(expected = IllegalStateException.class)
    public void testAgregarRecursoConCodigoVacio() {
    GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

    RecursoTIC recurso = new RecursoTIC.Builder()
            .codigo("")
            .nombre("recurso vacio")
            .estado("disponible")
            .build();

    gestor.agregarRecurso(recurso); // Deberia de lanzar un illegal argument exception
}

    @Test
    public void crearRecursoBien() {
        GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

        RecursoTIC recurso = new RecursoTIC.Builder()
                .codigo("R001")
                .nombre("recurso 1")
                .estado("disponible")
                .build();

        gestor.agregarRecurso(recurso);

        RecursoTIC recursoCreado = gestor.buscarRecurso("R001");
        assertNotNull(recursoCreado);
        assertEquals(recurso.getCodigo(), recursoCreado.getCodigo());
        assertEquals(recurso.getNombre(), recursoCreado.getNombre());
        assertEquals(recurso.getEstado(), recursoCreado.getEstado());
    }


    @Test
    public void listarRecursosListaVacia() {
        GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

        List<RecursoTIC> recursos = gestor.listarRecursos();

        assertTrue(recursos.isEmpty());
    }


    @Test
    public void testActualizarRecursoConCodigoExistenteYRecursoValido() {
        GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

        RecursoTIC recursoOriginal = new RecursoTIC.Builder()
                .codigo("R001")
                .nombre("recurso 1")
                .estado("disponible")
                .build();
        RecursoTIC recursoNuevo = new RecursoTIC.Builder()
                .codigo("R001")
                .nombre("recurso 1 actualizado")
                .estado("ocupado")
                .build();

        gestor.agregarRecurso(recursoOriginal);
        boolean actualizadoCorrectamente = gestor.actualizarRecurso("R001", recursoNuevo);

        assertTrue(actualizadoCorrectamente);
        RecursoTIC recursoActualizado = gestor.buscarRecurso("R001");
        assertEquals(recursoNuevo.getNombre(), recursoActualizado.getNombre());
        assertEquals(recursoNuevo.getEstado(), recursoActualizado.getEstado());
    }
    
    @Test
    public void testBuscarRecursoConCodigoQueNoExiste() {
        GestorRecursosTIC gestor = GestorRecursosTIC.getInstance();

        RecursoTIC recurso1 = new RecursoTIC.Builder()
                .codigo("R001")
                .nombre("recurso 1")
                .estado("disponible")
                .build();
        RecursoTIC recurso2 = new RecursoTIC.Builder()
                .codigo("R002")
                .nombre("recurso 2")
                .estado("ocupado")
                .build();

        gestor.agregarRecurso(recurso1);
        gestor.agregarRecurso(recurso2);

        RecursoTIC recursoNoEncontrado = gestor.buscarRecurso("R003");
        assertNull(recursoNoEncontrado);
    }
}
