package com.restaurante.restaurante.controllers;

import com.restaurante.restaurante.constantes.TipoCliente;
import com.restaurante.restaurante.dto.clienteDTO.ResponderClienteDTO;
import com.restaurante.restaurante.models.Cliente;
import com.restaurante.restaurante.services.ClienteServices;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    private final WebTestClient webTestClient;
    private final ClienteServices clienteServices;

    public ClienteControllerTest(){
        clienteServices = mock(ClienteServices.class);
        webTestClient = WebTestClient.bindToController(new ClienteController(clienteServices)).build();
    }

    @Test
    @DisplayName("Crear cliente")
    void agregarCliente(){

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com","082383823", TipoCliente.COMUN);

        when(clienteServices.agregarCliente(any(Cliente.class))).thenReturn(cliente);

        webTestClient
                .post()
                .uri("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cliente)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cliente.class)
                .value(cliente1 -> {
                   assertEquals(cliente.getId(),cliente1.getId());
                    assertEquals(cliente.getNombre(),cliente1.getNombre());
                    assertEquals(cliente.getEmail(),cliente1.getEmail());
                    assertEquals(cliente.getTelefono(),cliente1.getTelefono());
                    assertEquals(cliente.getTipoCliente(),cliente1.getTipoCliente());
                });

        Mockito.verify(clienteServices).agregarCliente(any(Cliente.class));

    }

    @Test
    @DisplayName("Obtener cliente por id")
    void obtenerCliente() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com","082383823", TipoCliente.COMUN);

        when(clienteServices.obtenerCliente(anyLong())).thenReturn(Optional.of(cliente));

        webTestClient.get()
                .uri("/api/clientes/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponderClienteDTO.class)
                .value(t -> {
                    assertEquals(cliente.getId(), t.getId());
                    assertEquals(cliente.getNombre(), t.getNombre());
                    assertEquals(cliente.getEmail(), t.getEmail());
                    assertEquals(cliente.getTelefono(), t.getTelefono());
                    assertEquals(cliente.getTipoCliente(), t.getTipoCliente());
                });

        Mockito.verify(clienteServices).obtenerCliente(anyLong());
    }

    @Test
    @DisplayName("Obtener todos los clientes")
    void listarClientes() {

        when(clienteServices.listarClientes()).thenReturn(getClientes());

        webTestClient.get()
                .uri("/api/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Cliente.class)
                .hasSize(3)
                .value(t -> {
                    assertEquals("Juan", t.get(0).getNombre());
                    assertEquals("Ana", t.get(1).getNombre());
                    assertEquals("Carlos", t.get(2).getNombre());
                });

        Mockito.verify(clienteServices).listarClientes();
    }

    /*----------------------*/

    @Test
    @DisplayName("Actualizar cliente")
    void actualizarCliente() {

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com","082383823", TipoCliente.COMUN);

        when(clienteServices.actualizarCliente(anyLong(), any(Cliente.class))).thenReturn(cliente);

        webTestClient.put()
                .uri("/api/clientes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cliente)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Se ha actualizado exitosamente el cliente", response));

        Mockito.verify(clienteServices).actualizarCliente(anyLong(), any(Cliente.class));
    }

    @Test
    void testActualizarClienteNotFound() {
        Long clienteIdInexistente = -999L;

        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com","082383823", TipoCliente.COMUN);

        when(clienteServices.actualizarCliente(clienteIdInexistente, cliente))
                .thenThrow(new RuntimeException("Cliente no encontrado"));

        webTestClient.put()
                .uri("/clientes/{id}", clienteIdInexistente)
                .body(Mono.just(cliente), Cliente.class)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class);
    }

    @Test
    @DisplayName("Eliminar cliente")
    void eliminarCliente() {

        Mockito.doNothing().when(clienteServices).eliminarCliente(anyLong());

        webTestClient.delete()
                .uri("/api/clientes/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(clienteServices).eliminarCliente(anyLong());
    }

    private List<Cliente> getClientes() {
        return List.of(
                new Cliente(1L, "Juan", "juan@example.com", "082383823",TipoCliente.COMUN),
                new Cliente(2L, "Ana", "ana@example.com", "6878768678",TipoCliente.COMUN),
                new Cliente(3L, "Carlos", "carlos@example.com", "324324235",TipoCliente.COMUN)
        );
    }

}