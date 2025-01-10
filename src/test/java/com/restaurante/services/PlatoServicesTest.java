package com.restaurante.services;

import com.restaurante.constantes.TipoPlato;
import com.restaurante.dto.platoDTO.ResponderPlatoDTO;
import com.restaurante.models.Menu;
import com.restaurante.models.Plato;
import com.restaurante.repositories.PlatoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlatoServicesTest {


    @Mock
    private PlatoRepositorio platoRepositorio;

    @InjectMocks
    private PlatoServices platoServices;

    private Plato plato;
    private Plato platoActualizado;
    private ResponderPlatoDTO responderPlatoDTO;
    private Plato plato1;
    private Plato plato2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        plato1 = new Plato(1L, "Plato 1", 10.0, TipoPlato.COMUN, new Menu());
        plato2 = new Plato(2L, "Plato 2", 15.0, TipoPlato.POPULAR, new Menu());
        plato = new Plato(1L, "Plato 1", 15.0, TipoPlato.COMUN, new Menu());
        platoActualizado = new Plato(1L, "Plato Actualizado", 20.0, TipoPlato.COMUN, new Menu());
        responderPlatoDTO = new ResponderPlatoDTO(plato.getId(), plato.getNombre(), plato.getPrecio(), plato.getTipoPlato(), plato.getMenu().getId());
    }

    @Test
    void testAgregarPlato() {
        when(platoRepositorio.save(any(Plato.class))).thenReturn(plato);

        Plato resultado = platoServices.agregarPlato(plato);

        assertNotNull(resultado);
        assertEquals(plato.getNombre(), resultado.getNombre());
        assertEquals(TipoPlato.COMUN, resultado.getTipoPlato());
        verify(platoRepositorio, times(1)).save(plato);
    }


    @Test
    void testObtenerPlato() {
        when(platoRepositorio.findById(plato.getId())).thenReturn(Optional.of(plato));

        Optional<Plato> resultado = platoServices.obtenerPlato(plato.getId());

        assertTrue(resultado.isPresent());
        assertEquals(plato.getId(), resultado.get().getId());
        verify(platoRepositorio, times(1)).findById(plato.getId());
    }

    @Test
    void testObtenerPlatoNoExistente() {
        when(platoRepositorio.findById(99L)).thenReturn(Optional.empty());

        Optional<Plato> resultado = platoServices.obtenerPlato(99L);

        assertFalse(resultado.isPresent());
        verify(platoRepositorio, times(1)).findById(99L);
    }

    @Test
    void testListarPlato() {
        List<Plato> platos = Arrays.asList(plato);
        when(platoRepositorio.findAll()).thenReturn(platos);

        List<ResponderPlatoDTO> resultado = platoServices.listarPlato();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(plato.getId(), resultado.get(0).getId());
        verify(platoRepositorio, times(1)).findAll();
    }

    @Test
    void testActualizarPlato() {
        when(platoRepositorio.findById(plato.getId())).thenReturn(Optional.of(plato));
        when(platoRepositorio.save(plato)).thenReturn(platoActualizado);

        Plato resultado = platoServices.actualizarPlato(plato.getId(), platoActualizado);

        assertNotNull(resultado);
        assertEquals(platoActualizado.getNombre(), resultado.getNombre());
        verify(platoRepositorio, times(1)).findById(plato.getId());
        verify(platoRepositorio, times(1)).save(plato);
    }

    @Test
    void testActualizarPlatoNoExistente() {
        when(platoRepositorio.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            platoServices.actualizarPlato(99L, platoActualizado);
        });

        assertEquals("Plato con el id 99 no pudo ser actualizado", exception.getMessage());
        verify(platoRepositorio, times(1)).findById(99L);
    }

    @Test
    void testEliminarPlato() {
        doNothing().when(platoRepositorio).deleteById(plato.getId());

        platoServices.eliminarPlato(plato.getId());

        verify(platoRepositorio, times(1)).deleteById(plato.getId());
    }

    @Test
    void testObtenerTodosPlatoPorId() {
        List<Long> ids = Arrays.asList(plato1.getId(), plato2.getId());
        when(platoRepositorio.findAllById(ids)).thenReturn(Arrays.asList(plato1, plato2));
        List<Plato> platos = platoServices.obtenerTodosPlatoPorId(ids);
        assertNotNull(platos);
        assertEquals(2, platos.size());
        assertEquals(plato1.getId(), platos.get(0).getId());
        assertEquals(plato2.getId(), platos.get(1).getId());
        verify(platoRepositorio, times(1)).findAllById(ids);
    }

    @Test
    void testObtenerTodosPlatoPorIdConListaVacia() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(platoRepositorio.findAllById(ids)).thenReturn(Arrays.asList());
        List<Plato> platos = platoServices.obtenerTodosPlatoPorId(ids);
        assertNotNull(platos);
        assertTrue(platos.isEmpty());
        verify(platoRepositorio, times(1)).findAllById(ids);
    }

    @Test
    void testObtenerTodosPlatoPorIdConAlgunosIdsNoExistentes() {
        List<Long> ids = Arrays.asList(plato1.getId(), 99L);
        when(platoRepositorio.findAllById(ids)).thenReturn(Arrays.asList(plato1));
        List<Plato> platos = platoServices.obtenerTodosPlatoPorId(ids);
        assertNotNull(platos);
        assertEquals(1, platos.size());
        assertEquals(plato1.getId(), platos.get(0).getId());
        verify(platoRepositorio, times(1)).findAllById(ids);
    }

}