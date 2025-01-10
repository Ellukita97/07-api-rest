package com.restaurante.restaurante.services;

import com.restaurante.restaurante.dto.menuDTO.ResponderMenuDTO;
import com.restaurante.restaurante.models.Menu;
import com.restaurante.restaurante.models.Plato;
import com.restaurante.restaurante.repositories.MenuRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuServicesTest {

    @Mock
    private MenuRepositorio menuRepositorio;

    @InjectMocks
    private MenuServices menuServices;

    private Menu menu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        menu = new Menu();
        menu.setId(1L);
        menu.setNombre("Menú de prueba");
    }

    @Test
    public void testAgregarMenu() {
        when(menuRepositorio.save(any(Menu.class))).thenReturn(menu);

        Menu resultado = menuServices.agregarMenu(menu);

        assertNotNull(resultado);
        assertEquals("Menú de prueba", resultado.getNombre());
        verify(menuRepositorio, times(1)).save(menu);
    }

    @Test
    public void testObtenerMenu() {
        when(menuRepositorio.findById(1L)).thenReturn(Optional.of(menu));

        Optional<Menu> resultado = menuServices.obtenerMenu(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Menú de prueba", resultado.get().getNombre());
        verify(menuRepositorio, times(1)).findById(1L);
    }

    @Test
    public void testListarMenu() {
        Menu otroMenu = new Menu();
        otroMenu.setId(2L);
        otroMenu.setNombre("Otro Menú");

        when(menuRepositorio.findAll()).thenReturn(Arrays.asList(menu, otroMenu));

        List<ResponderMenuDTO> resultado = menuServices.listarMenu();

        assertEquals(2, resultado.size());
        assertEquals("Menú de prueba", resultado.get(0).getNombre());
        assertEquals("Otro Menú", resultado.get(1).getNombre());
        verify(menuRepositorio, times(1)).findAll();
    }

    @Test
    public void testActualizarMenu() {
        Menu menuActualizado = new Menu();
        menuActualizado.setNombre("Menú actualizado");
        menuActualizado.setPlatos(Arrays.asList(new Plato(), new Plato()));

        when(menuRepositorio.findById(1L)).thenReturn(Optional.of(menu));
        when(menuRepositorio.save(any(Menu.class))).thenReturn(menuActualizado);
        Menu resultado = menuServices.actualizarMenu(1L, menuActualizado);

        assertEquals("Menú actualizado", resultado.getNombre());
        ArgumentCaptor<Menu> captor = ArgumentCaptor.forClass(Menu.class);
        verify(menuRepositorio).save(captor.capture());

        Menu menuGuardado = captor.getValue();

        assertEquals("Menú actualizado", menuGuardado.getNombre());
        assertEquals(2, menuGuardado.getPlatos().size());

        verify(menuRepositorio, times(1)).findById(1L);
        verify(menuRepositorio, times(1)).save(any(Menu.class));
    }

    @Test
    public void testEliminarMenu() {
        doNothing().when(menuRepositorio).deleteById(1L);

        menuServices.eliminarMenu(1L);

        verify(menuRepositorio, times(1)).deleteById(1L);
    }

    @Test
    public void testActualizarMenuNoEncontrado() {
        Menu menuActualizado = new Menu();
        menuActualizado.setNombre("Menú actualizado");

        when(menuRepositorio.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            menuServices.actualizarMenu(1L, menuActualizado);
        });

        assertEquals("Menu con el id 1 no pudo ser actualizado", exception.getMessage());
        verify(menuRepositorio, times(1)).findById(1L);
    }

}