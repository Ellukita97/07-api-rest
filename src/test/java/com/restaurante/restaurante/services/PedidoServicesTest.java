package com.restaurante.restaurante.services;

import com.restaurante.restaurante.constantes.TipoCliente;
import com.restaurante.restaurante.constantes.TipoPlato;
import com.restaurante.restaurante.dto.pedidosDTO.ResponderPedidosDTO;
import com.restaurante.restaurante.models.Cliente;
import com.restaurante.restaurante.models.Menu;
import com.restaurante.restaurante.models.Pedido;
import com.restaurante.restaurante.models.Plato;
import com.restaurante.restaurante.patronesDeDisenio.strategy.PrecioEstrategia;
import com.restaurante.restaurante.patronesDeDisenio.strategy.PrecioRegularEstrategia;
import com.restaurante.restaurante.patronesDeDisenio.strategy.PrecioVIPEstrategia;
import com.restaurante.restaurante.repositories.ClienteRepositorio;
import com.restaurante.restaurante.repositories.PedidoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoServicesTest {

    @Mock
    private PedidoRepositorio repositorioPedido;

    @Mock
    private ClienteRepositorio repositorioCliente;

    @Mock
    private PrecioEstrategia precioVIPEstrategia;

    @InjectMocks
    private PedidoServices pedidoServices;

    private Cliente cliente;
    private Pedido pedido;
    private Plato plato;
    private Pedido pedido1;
    private Pedido pedido2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setTipoCliente(TipoCliente.COMUN);

        Menu menu = new Menu(1L, "Menú Especial");

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCliente(cliente);
        pedido.setPrecio(100.0);
        List<Plato> platos = List.of(
                new Plato(1L, "Plato 1", 10.0, TipoPlato.COMUN, menu),
                new Plato(2L, "Plato 2", 20.0, TipoPlato.COMUN, menu)
        );
        pedido.setPlatos(platos);

        pedido1 = new Pedido(1L, 30.0, cliente);
        pedido2 = new Pedido(2L, 45.0, cliente);


        plato = new Plato();
        plato.setId(1L);
        plato.setNombre("Plato Test");
    }

    @Test
    void testAgregarPedido() {
        when(repositorioCliente.findById(1L)).thenReturn(Optional.of(cliente));
        when(repositorioPedido.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoServices.agregarPedido(pedido, 1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repositorioCliente, times(1)).findById(1L);
        verify(repositorioPedido, times(1)).save(pedido);
    }

    @Test
    void testObtenerPedido() {
        when(repositorioPedido.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido resultado = pedidoServices.obtenerPedido(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(repositorioPedido, times(1)).findById(1L);
    }

    @Test
    void testObtenerPedidoNotFound() {
        when(repositorioPedido.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoServices.obtenerPedido(1L);
        });

        assertEquals("Pedido no encontrado.", exception.getMessage());
    }

    @Test
    void testActualizarPedido() {
        Pedido pedidoActualizado = new Pedido();
        pedidoActualizado.setId(1L);
        pedidoActualizado.setCliente(cliente);
        pedidoActualizado.setPlatos(List.of(plato));
        pedidoActualizado.setPrecio(150.0);

        when(repositorioPedido.findById(1L)).thenReturn(Optional.of(pedido));
        when(repositorioPedido.save(any(Pedido.class))).thenReturn(pedidoActualizado);

        Pedido resultado = pedidoServices.actualizarPedido(1L, pedidoActualizado);

        assertNotNull(resultado);
        assertEquals(150.0, resultado.getPrecio());
        verify(repositorioPedido, times(1)).findById(1L);
        verify(repositorioPedido, times(1)).save(any(Pedido.class));
    }

    @Test
    void testEliminarPedido() {
        when(repositorioPedido.findById(1L)).thenReturn(Optional.of(pedido));

        pedidoServices.eliminarPedido(1L);

        verify(repositorioPedido, times(1)).delete(pedido);
    }

    @Test
    void testEliminarPedidoNotFound() {
        when(repositorioPedido.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoServices.eliminarPedido(1L);
        });

        assertEquals("Pedido no encontrado.", exception.getMessage());
    }

    @Test
    void testAplicarDescuentoATodosLosPedidosDeCliente() {
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        pedido1.setPrecio(100.0);
        pedido1.setCliente(cliente);

        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);
        pedido2.setPrecio(200.0);
        pedido2.setCliente(cliente);

        when(repositorioPedido.findByClienteId(1L)).thenReturn(List.of(pedido1, pedido2));
        pedidoServices.aplicarDescuentoATodosLosPedidosDeCliente(1L);

        assertEquals(97.61999999999999, pedido1.getPrecio(), 0.00001);
        assertEquals(195.23999999999998, pedido2.getPrecio(), 0.00001);

    }

    @Test
    void testListarPedido() {
        List<Pedido> pedidos = Arrays.asList(pedido);
        when(repositorioPedido.findAll()).thenReturn(pedidos);

        List<ResponderPedidosDTO> resultado = pedidoServices.listarPedido();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pedido.getId(), resultado.get(0).getId());
        assertEquals(pedido.getPrecio(), resultado.get(0).getPrecio());
        assertEquals(pedido.getCliente().getId(), resultado.get(0).getIdCliente());
        verify(repositorioPedido, times(1)).findAll();
    }

    @Test
    void testListarPedidoVacio() {
        when(repositorioPedido.findAll()).thenReturn(Arrays.asList());

        List<ResponderPedidosDTO> resultado = pedidoServices.listarPedido();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repositorioPedido, times(1)).findAll();
    }


    @Test
    void testVerificarPlatoMasDe100Veces() {
        when(repositorioPedido.contarVecesQuePlatoFuePedido(1L)).thenReturn(101L);

        boolean resultado = pedidoServices.verificarPlatoMasDe100Veces(1L);

        assertTrue(resultado);
        verify(repositorioPedido, times(1)).contarVecesQuePlatoFuePedido(1L);
    }

    @Test
    void testObtenerPedidosPorCliente() {
        when(repositorioPedido.findByCliente_Id(cliente.getId())).thenReturn(Arrays.asList(pedido1, pedido2));
        List<Pedido> pedidos = pedidoServices.obtenerPedidosPorCliente(cliente.getId());

        assertNotNull(pedidos);
        assertEquals(2, pedidos.size());
        assertEquals(pedido1.getId(), pedidos.get(0).getId());
        assertEquals(pedido2.getId(), pedidos.get(1).getId());

        verify(repositorioPedido, times(1)).findByCliente_Id(cliente.getId());
    }

    @Test
    void testObtenerPedidosPorClienteSinPedidos() {
        when(repositorioPedido.findByCliente_Id(cliente.getId())).thenReturn(Arrays.asList());
        List<Pedido> pedidos = pedidoServices.obtenerPedidosPorCliente(cliente.getId());
        assertNotNull(pedidos);
        assertTrue(pedidos.isEmpty());
        verify(repositorioPedido, times(1)).findByCliente_Id(cliente.getId());
    }

    @Test
    void testObtenerPedidosPorClienteConClienteNoExistente() {
        Long clienteIdInexistente = 99L;
        when(repositorioPedido.findByCliente_Id(clienteIdInexistente)).thenReturn(Arrays.asList());
        List<Pedido> pedidos = pedidoServices.obtenerPedidosPorCliente(clienteIdInexistente);

        assertNotNull(pedidos);
        assertTrue(pedidos.isEmpty());
        verify(repositorioPedido, times(1)).findByCliente_Id(clienteIdInexistente);
    }

    @Test
    void testContarPedidosPorClienteConPedidos() {
        when(repositorioPedido.countByClienteId(cliente.getId())).thenReturn(3L);
        long resultado = pedidoServices.contarPedidosPorCliente(cliente.getId());
        assertEquals(3L, resultado);
        verify(repositorioPedido, times(1)).countByClienteId(cliente.getId());
    }

    @Test
    void testContarPedidosPorClienteSinPedidos() {
        when(repositorioPedido.countByClienteId(cliente.getId())).thenReturn(0L);
        long resultado = pedidoServices.contarPedidosPorCliente(cliente.getId());
        assertEquals(0L, resultado);
        verify(repositorioPedido, times(1)).countByClienteId(cliente.getId());
    }

    @Test
    void testContarPedidosPorClienteConClienteNoExistente() {
        Long clienteIdInexistente = 99L;
        when(repositorioPedido.countByClienteId(clienteIdInexistente)).thenReturn(0L);
        long resultado = pedidoServices.contarPedidosPorCliente(clienteIdInexistente);
        assertEquals(0L, resultado);
        verify(repositorioPedido, times(1)).countByClienteId(clienteIdInexistente);
    }

    @Test
    void testObtenerEstrategiaPorTipoClienteFrecuente() {
        TipoCliente tipoCliente = TipoCliente.FRECUENTE;

        PrecioEstrategia estrategia = pedidoServices.obtenerEstrategiaPorTipoCliente(tipoCliente);
        assertTrue(estrategia instanceof PrecioVIPEstrategia);
        Menu menu = new Menu(1L, "Menú Especial");

        List<Plato> platos = List.of(
                new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu),
                new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu)
        );

        double precioCalculado = estrategia.calcularPrecio(platos);
        assertEquals(54.0, precioCalculado, 0.01);
    }

    @Test
    void testObtenerEstrategiaPorTipoClienteRegular() {
        TipoCliente tipoCliente = TipoCliente.COMUN;

        PrecioEstrategia estrategia = pedidoServices.obtenerEstrategiaPorTipoCliente(tipoCliente);
        assertTrue(estrategia instanceof PrecioRegularEstrategia);
        Menu menu = new Menu(1L, "Menú Especial");

        List<Plato> platos = List.of(
                new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu),
                new Plato(1L, "Plato 1", 30.0, TipoPlato.COMUN, menu)
        );

        double precioCalculado = estrategia.calcularPrecio(platos);
        assertEquals(60, precioCalculado, 0.01);
    }

    @Test
    void testVerificarPlatoMasDe100Veces_CuandoSeSuperanLas3Veces() {
        Long platoId = 1L;
        when(repositorioPedido.contarVecesQuePlatoFuePedido(platoId)).thenReturn(4L);
        boolean resultado = pedidoServices.verificarPlatoMasDe100Veces(platoId);
        assertTrue(resultado);
        verify(repositorioPedido, times(1)).contarVecesQuePlatoFuePedido(platoId);
    }


    @Test
    void testVerificarPlatoMasDe100Veces_CuandoNoHayPedidos() {
        Long platoId = 1L;
        when(repositorioPedido.contarVecesQuePlatoFuePedido(platoId)).thenReturn(0L);
        boolean resultado = pedidoServices.verificarPlatoMasDe100Veces(platoId);
        assertFalse(resultado);
        verify(repositorioPedido, times(1)).contarVecesQuePlatoFuePedido(platoId);
    }


    @Test
    void testCalcularPrecio() {
        Menu menu = new Menu(1L, "Menú Especial");
        List<Plato> platos = List.of(
                new Plato(1L, "Plato 1", 10.0, TipoPlato.COMUN, menu),
                new Plato(2L, "Plato 2", 20.0, TipoPlato.COMUN, menu)
        );
        Pedido pedido = new Pedido();
        pedido.setPlatos(platos);
        pedido.setCliente(cliente);

        when(precioVIPEstrategia.calcularPrecio(anyList())).thenReturn(120.0);

        Double resultado = pedidoServices.calcularPrecio(pedido);
        assertNotNull(resultado);
        assertEquals(30.0, resultado);
        verify(precioVIPEstrategia, times(1)).calcularPrecio(anyList());
    }

    @Test
    void testObtenerEstrategiaPorTipoCliente() {
        when(precioVIPEstrategia.calcularPrecio(anyList())).thenReturn(120.0);

        Double resultado = pedidoServices.calcularPrecio(pedido);

        assertEquals(30.0, resultado);
    }

}