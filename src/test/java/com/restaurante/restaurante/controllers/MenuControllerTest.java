package com.restaurante.restaurante.controllers;

import com.restaurante.restaurante.dto.menuDTO.RecibirMenuDTO;
import com.restaurante.restaurante.dto.menuDTO.ResponderMenuDTO;
import com.restaurante.restaurante.models.Menu;
import com.restaurante.restaurante.services.MenuServices;
import com.restaurante.restaurante.ulils.converters.MenuDTOConvertidor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuControllerTest {

    private final WebTestClient webTestClient;
    private final MenuServices menuServices;

    public MenuControllerTest() {
        menuServices = mock(MenuServices.class);
        webTestClient = WebTestClient.bindToController(new MenuController(menuServices)).build();
    }

    @Test
    @DisplayName("Crear menu")
    void agregarMenu() {

        RecibirMenuDTO menuRecibido = new RecibirMenuDTO("Menú Especial","");
        Menu menu = MenuDTOConvertidor.convertirAEntidad(menuRecibido);
        ResponderMenuDTO respuestaDTO = MenuDTOConvertidor.convertirDTO(menu);

        when(menuServices.agregarMenu(any(Menu.class))).thenReturn(menu);

        webTestClient
                .post()
                .uri("/api/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRecibido)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponderMenuDTO.class)
                .value(dto -> assertEquals(respuestaDTO.getNombre(), dto.getNombre()));

        Mockito.verify(menuServices).agregarMenu(any(Menu.class));
    }

    @Test
    @DisplayName("Obtener menu por id")
    void obtenerMenuPorId() {

        Menu menu = new Menu(1L, "Menú Especial","");
        ResponderMenuDTO respuestaDTO = MenuDTOConvertidor.convertirDTO(menu);

        when(menuServices.obtenerMenu(anyLong())).thenReturn(Optional.of(menu));

        webTestClient.get()
                .uri("/api/menus/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponderMenuDTO.class)
                .value(dto -> assertEquals(respuestaDTO.getNombre(), dto.getNombre()));

        Mockito.verify(menuServices).obtenerMenu(anyLong());
    }

    @Test
    @DisplayName("Obtener todos los menus")
    void listarMenus() {

        List<ResponderMenuDTO> menus = List.of(
                new ResponderMenuDTO(1L, "Menú Especial",""),
                new ResponderMenuDTO(2L, "Menú Vegano",""),
                new ResponderMenuDTO(3L, "Menú Infantil","")
        );

        when(menuServices.listarMenu()).thenReturn(menus);

        webTestClient.get()
                .uri("/api/menus")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResponderMenuDTO.class)
                .hasSize(3)
                .value(list -> {
                    assertEquals("Menú Especial", list.get(0).getNombre());
                    assertEquals("Menú Vegano", list.get(1).getNombre());
                    assertEquals("Menú Infantil", list.get(2).getNombre());
                });

        Mockito.verify(menuServices).listarMenu();
    }

    @Test
    @DisplayName("Actualizar menu")
    void actualizarMenu() {

        Menu menu = new Menu(1L, "Menú Especial","");
        when(menuServices.actualizarMenu(anyLong(), any(Menu.class))).thenReturn(menu);

        webTestClient.put()
                .uri("/api/menus/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menu)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Se ha actualizado exitosamente el menu", response));

        Mockito.verify(menuServices).actualizarMenu(anyLong(), any(Menu.class));
    }

    @Test
    void testActualizarMenuNotFound() {
        Long menuIdInexistente = 999L;

        Menu menu = new Menu(1L, "Menú Especial","");

        when(menuServices.actualizarMenu(menuIdInexistente, menu))
                .thenThrow(new RuntimeException("Menu no encontrado"));

        webTestClient.put()
                .uri("/menus/{id}", menuIdInexistente)
                .body(Mono.just(menu), Menu.class)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Eliminar menu")
    void eliminarMenu() {

        doNothing().when(menuServices).eliminarMenu(anyLong());

        webTestClient.delete()
                .uri("/api/menus/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(menuServices).eliminarMenu(anyLong());
    }

}