package com.restaurante.restaurante.services;

import com.restaurante.restaurante.constantes.TipoCliente;
import com.restaurante.restaurante.models.Cliente;
import com.restaurante.restaurante.repositories.ClienteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClienteServicesTest {


    @Mock
    private ClienteRepositorio repositorio;

    @InjectMocks
    private ClienteServices clienteServices;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAgregarCliente() {
        Cliente cliente = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);

        when(repositorio.save(cliente)).thenReturn(cliente);

        Cliente clienteGuardado = clienteServices.agregarCliente(cliente);

        assertNotNull(clienteGuardado);
        assertEquals("Juan", clienteGuardado.getNombre());
        assertEquals(TipoCliente.COMUN, clienteGuardado.getTipoCliente());
        verify(repositorio, times(1)).save(cliente);
    }

    @Test
    public void testObtenerCliente() {
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNombre("Carlos");

        when(repositorio.findById(clienteId)).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteEncontrado = clienteServices.obtenerCliente(clienteId);

        assertTrue(clienteEncontrado.isPresent());
        assertEquals("Carlos", clienteEncontrado.get().getNombre());
        verify(repositorio, times(1)).findById(clienteId);
    }

    @Test
    public void testListarClientes() {
        Cliente cliente1 = new Cliente(1L, "Juan", "juan@juan.com", "082383823", TipoCliente.COMUN);
        Cliente cliente2 = new Cliente(2L, "Maria", "juan@juan.com", "082383823", TipoCliente.COMUN);

        when(repositorio.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = clienteServices.listarClientes();

        assertNotNull(clientes);
        assertEquals(2, clientes.size());

        assertEquals("Juan", clientes.get(0).getNombre());
        assertEquals("Maria", clientes.get(1).getNombre());

        verify(repositorio, times(1)).findAll();
    }

    @Test
    public void testActualizarCliente() {
        Long clienteId = 1L;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(clienteId);
        clienteExistente.setNombre("Carlos");
        clienteExistente.setEmail("carlos@example.com");

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Carlos Actualizado");
        clienteActualizado.setEmail("carlosact@example.com");

        when(repositorio.findById(clienteId)).thenReturn(Optional.of(clienteExistente));
        when(repositorio.save(any(Cliente.class))).thenReturn(clienteActualizado);

        Cliente clienteResultante = clienteServices.actualizarCliente(clienteId, clienteActualizado);

        assertNotNull(clienteResultante);
        assertEquals("Carlos Actualizado", clienteResultante.getNombre());
        assertEquals("carlosact@example.com", clienteResultante.getEmail());
        verify(repositorio, times(1)).findById(clienteId);
        verify(repositorio, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testEliminarCliente() {
        Long clienteId = 1L;

        doNothing().when(repositorio).deleteById(clienteId);
        clienteServices.eliminarCliente(clienteId);

        verify(repositorio, times(1)).deleteById(clienteId);
    }


}