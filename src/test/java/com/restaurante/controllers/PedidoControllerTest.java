package com.restaurante.controllers;

import com.restaurante.constantes.TipoCliente;
import com.restaurante.constantes.TipoPlato;
import com.restaurante.dto.pedidosDTO.RecibirPedidosDTO;
import com.restaurante.dto.pedidosDTO.ResponderPedidosDTO;
import com.restaurante.models.Cliente;
import com.restaurante.models.Menu;
import com.restaurante.models.Pedido;
import com.restaurante.models.Plato;
import com.restaurante.services.ClienteServices;
import com.restaurante.services.PedidoServices;
import com.restaurante.services.PlatoServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoControllerTest {

    private final WebTestClient webTestClient;
    private final PedidoServices servicesPedido;
    private final PlatoServices servicesPlato;
    private final ClienteServices clienteServices;

    public PedidoControllerTest() {
        servicesPedido = mock(PedidoServices.class);
        servicesPlato = mock(PlatoServices.class);
        clienteServices = mock(ClienteServices.class);
        webTestClient = WebTestClient.bindToController(new PedidoController(servicesPedido, servicesPlato, clienteServices)).build();
    }

    @Test
    @DisplayName("Agregar pedido correctamente")
    void agregarPedido() {
        RecibirPedidosDTO recibirPedidosDTO = new RecibirPedidosDTO(100.0, 1L);
        Cliente cliente = new Cliente(1L, "Juan Perez", "juan@ejemplo.com", "123456789", TipoCliente.COMUN);

        when(clienteServices.obtenerCliente(1L)).thenReturn(java.util.Optional.of(new Cliente()));

        Pedido pedidoSimulado = new Pedido();
        pedidoSimulado.setId(1L);
        pedidoSimulado.setPrecio(100.0);
        when(servicesPedido.agregarPedido(Mockito.any(Pedido.class), Mockito.anyLong())).thenReturn(pedidoSimulado);

        webTestClient.post().uri("/api/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedidoSimulado)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Pedido.class)
                .value(pedido1 -> {
                    assertEquals(pedidoSimulado.getId(),pedido1.getId());
                    assertEquals(pedidoSimulado.getPrecio(), pedido1.getPrecio());
                    assertEquals(pedidoSimulado.getPlatos(), pedido1.getPlatos());
                    assertEquals(pedidoSimulado.getCliente(), pedido1.getCliente());
                });

        Mockito.verify(servicesPedido).agregarPedido(any(Pedido.class), anyLong());
    }

    @Test
    @DisplayName("Obtener pedido por id")
    void obtenerPedido() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Pedido pedido = new Pedido(1L, 100.0, cliente);

        when(servicesPedido.obtenerPedido(anyLong())).thenReturn(pedido);

        webTestClient.get()
                .uri("/api/pedido/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponderPedidosDTO.class)
                .value(respuesta -> {
                    assertEquals(pedido.getId(), respuesta.getId());
                    assertEquals(pedido.getPrecio(), respuesta.getPrecio());
                    assertEquals(pedido.getCliente().getId(), respuesta.getIdCliente());
                });

        verify(servicesPedido).obtenerPedido(anyLong());
    }

    @Test
    @DisplayName("Listar pedidos")
    void listarPedidos() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Cliente cliente2 = new Cliente(2L, "Andres", "andres@andres.com", "12422144", TipoCliente.COMUN);
        List<ResponderPedidosDTO> pedidos = List.of(new ResponderPedidosDTO(1L, 100.0, cliente.getId()), new ResponderPedidosDTO(2L, 150.0, cliente2.getId()));

        when(servicesPedido.listarPedido()).thenReturn(pedidos);

        webTestClient.get()
                .uri("/api/pedido")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ResponderPedidosDTO.class)
                .hasSize(2)
                .value(respuesta -> {
                    assertEquals(1L, respuesta.get(0).getIdCliente());
                    assertEquals(2L, respuesta.get(1).getIdCliente());
                });

        verify(servicesPedido).listarPedido();
    }

    @Test
    @DisplayName("Actualizar pedido")
    void actualizarPedido() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Pedido pedido = new Pedido(1L, 100.0, cliente);

        when(servicesPedido.actualizarPedido(anyLong(), any(Pedido.class))).thenReturn(pedido);

        webTestClient.put()
                .uri("/api/pedido/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedido)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> {
                    assertEquals("Se ha actualizado exitosamente el pedido", response);
                });

        verify(servicesPedido).actualizarPedido(anyLong(), any(Pedido.class));
    }

    @Test
    void testActualizarPedidoNotFound() {
        Long pedidoIdInexistente = 999L;

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Pedido pedido = new Pedido(1L, 100.0, cliente);

        when(servicesPedido.actualizarPedido(pedidoIdInexistente, pedido))
                .thenThrow(new RuntimeException("Pedido no encontrado"));

        webTestClient.put()
                .uri("/pedidos/{id}", pedidoIdInexistente)
                .body(Mono.just(pedido), Pedido.class)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Eliminar pedido")
    void eliminarPedido() {

        doNothing().when(servicesPedido).eliminarPedido(anyLong());

        webTestClient.delete()
                .uri("/api/pedido/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();

        verify(servicesPedido).eliminarPedido(anyLong());
    }

    @Test
    @DisplayName("Obtener pedidos por cliente")
    void obtenerPedidosPorCliente() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        List<Pedido> pedidos = List.of(new Pedido(1L, 100.0, cliente));

        when(servicesPedido.obtenerPedidosPorCliente(anyLong())).thenReturn(pedidos);

        webTestClient.get()
                .uri("/api/pedido/cliente/{clienteId}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Pedido.class)
                .hasSize(1)
                .value(pedidoList -> {
                    assertEquals(1L, pedidoList.get(0).getId());
                });

        //verify(servicesPedido).obtenerPedidosPorCliente(anyLong());
    }

    @Test
    void testObtenerPedidosPorClienteNotFound() {
        Long clienteIdInexistente = 999L;

        when(servicesPedido.obtenerPedidosPorCliente(clienteIdInexistente))
                .thenThrow(new RuntimeException("Cliente no tiene pedidos"));

        webTestClient.get()
                .uri("/cliente/{clienteId}", clienteIdInexistente)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

    @Test
    @DisplayName("Añadir platos a pedido")
    void addPlatosToPedido() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Pedido pedido = new Pedido(1L, 0.0, cliente);
        Menu menu = new Menu(1L, "Menú Especial");
        Plato plato = new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu);
        List<Long> platosIds = List.of(1L);

        when(servicesPedido.obtenerPedido(anyLong())).thenReturn(pedido);
        when(servicesPlato.obtenerTodosPlatoPorId(anyList())).thenReturn(List.of(plato));
        when(servicesPedido.agregarPedido(any(Pedido.class), anyLong())).thenReturn(pedido);

        webTestClient.post()
                .uri("/api/pedido/{pedidoId}/platos", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(platosIds)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Pedido.class)
                .value(updatedPedido -> {
                    assertEquals(30.0, updatedPedido.getPrecio());
                });

        verify(servicesPedido).agregarPedido(any(Pedido.class), anyLong());
    }

    @Test
    void testAddPlatosToPedidoEmptyPlatos() {
        Long pedidoId = 1L;
        List<Long> platosIds = List.of(1L, 2L, 3L);

        when(servicesPlato.obtenerTodosPlatoPorId(platosIds))
                .thenReturn(List.of());

        webTestClient.post()
                .uri("/api/pedido/{pedidoId}/platos", pedidoId)
                .bodyValue(platosIds)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().isEmpty();
    }

    @Test
    @DisplayName("Calcular precio de pedido")
    void calcularPrecio() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Pedido pedido = new Pedido(1L, 100.0, cliente);
        Menu menu = new Menu(1L, "Menú Especial");
        Plato plato = new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu);
        pedido.getPlatos().add(plato);

        when(servicesPedido.calcularPrecio(any(Pedido.class))).thenReturn(130.0);

        webTestClient.post()
                .uri("/api/pedido/calcularPrecio")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedido)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .value(price -> {
                    assertEquals(130.0, price);
                });

        verify(servicesPedido).calcularPrecio(any(Pedido.class));
    }

}