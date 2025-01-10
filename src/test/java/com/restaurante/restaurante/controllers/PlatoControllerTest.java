package com.restaurante.restaurante.controllers;

import com.restaurante.restaurante.constantes.TipoPlato;
import com.restaurante.restaurante.dto.platoDTO.RecibirPlatoDTO;
import com.restaurante.restaurante.dto.platoDTO.ResponderPlatoDTO;
import com.restaurante.restaurante.models.Menu;
import com.restaurante.restaurante.models.Plato;
import com.restaurante.restaurante.services.MenuServices;
import com.restaurante.restaurante.services.PlatoServices;
import com.restaurante.restaurante.ulils.converters.PlatoDTOConvertidor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlatoControllerTest {

    private final WebTestClient webTestClient;
    private final PlatoServices platoServices;
    private final MenuServices menuServices;

    public PlatoControllerTest() {
        platoServices = mock(PlatoServices.class);
        menuServices = mock(MenuServices.class);
        webTestClient = WebTestClient.bindToController(new PlatoController(platoServices, menuServices)).build();
    }

    @Test
    @DisplayName("Crear plato")
    void agregarPlato() {
        RecibirPlatoDTO platoRecibido = new RecibirPlatoDTO("Plato 1", 1L, 30.0);

        Menu menu = new Menu(1L, "Menú Especial");
        Plato plato = new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu);

        when(menuServices.obtenerMenu(anyLong())).thenReturn(Optional.of(menu));
        when(platoServices.agregarPlato(any(Plato.class))).thenReturn(plato);

        ResponderPlatoDTO respuestaDTO = PlatoDTOConvertidor.convertirDTO(plato);

        webTestClient
                .post()
                .uri("/api/platos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(platoRecibido)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponderPlatoDTO.class)
                .value(respuesta -> {
                    assertEquals(respuestaDTO.getNombre(), respuesta.getNombre());
                    assertEquals(respuestaDTO.getPrecio(), respuesta.getPrecio());
                    assertEquals(respuestaDTO.getIdmenu(), respuesta.getIdmenu());
                });

        Mockito.verify(menuServices).obtenerMenu(anyLong());
        Mockito.verify(platoServices).agregarPlato(any(Plato.class));
    }

    @Test
    @DisplayName("Obtener plato por id")
    void obtenerPlatoPorId() {
        Menu menu = new Menu(1L, "Menú Especial");
        Plato plato = new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu);

        when(platoServices.obtenerPlato(anyLong())).thenReturn(Optional.of(plato));

        ResponderPlatoDTO respuestaDTO = PlatoDTOConvertidor.convertirDTO(Optional.of(plato));

        webTestClient.get()
                .uri("/api/platos/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponderPlatoDTO.class)
                .value(respuesta -> {
                    assertEquals(respuestaDTO.getNombre(), respuesta.getNombre());
                    assertEquals(respuestaDTO.getPrecio(), respuesta.getPrecio());
                    assertEquals(respuestaDTO.getIdmenu(), respuesta.getIdmenu());
                });

        Mockito.verify(platoServices).obtenerPlato(anyLong());
    }

    @Test
    @DisplayName("Listar todos los platos")
    void listarPlato() {

        Menu menu = new Menu(1L, "Menú Especial");

        List<ResponderPlatoDTO> platos = List.of(
                new ResponderPlatoDTO(1L, "Plato 1", 10.0, TipoPlato.COMUN, 1L),
                new ResponderPlatoDTO(2L, "Plato 2", 20.0, TipoPlato.COMUN, 1L)
        );

        when(platoServices.listarPlato()).thenReturn(platos);

        webTestClient.get()
                .uri("/api/platos")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResponderPlatoDTO.class)
                .hasSize(2)
                .value(platosList -> {
                    assertEquals("Plato 1", platosList.get(0).getNombre());
                    assertEquals("Plato 2", platosList.get(1).getNombre());
                });

        Mockito.verify(platoServices).listarPlato();
    }

    @Test
    @DisplayName("Actualizar plato")
    void actualizarPlato() {
        Menu menu = new Menu(1L, "Menú Especial");
        Plato plato = new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu);

        when(platoServices.actualizarPlato(anyLong(), any(Plato.class))).thenReturn(plato);

        webTestClient.put()
                .uri("/api/platos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(plato)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Se ha actualizado exitosamente el plato", response));

        Mockito.verify(platoServices).actualizarPlato(anyLong(), any(Plato.class));
    }

    @Test
    @DisplayName("Eliminar plato")
    void eliminarPlato() {
        doNothing().when(platoServices).eliminarPlato(anyLong());

        webTestClient.delete()
                .uri("/api/platos/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(platoServices).eliminarPlato(anyLong());
    }

}