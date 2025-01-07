package com.restaurante.controllers;

import com.restaurante.dto.pedidosDTO.RecibirPedidosDTO;
import com.restaurante.dto.pedidosDTO.ResponderPedidosDTO;
import com.restaurante.models.Cliente;
import com.restaurante.models.Pedido;
import com.restaurante.models.Plato;
import com.restaurante.services.ClienteServices;
import com.restaurante.services.PedidoServices;
import com.restaurante.services.PlatoServices;
import com.restaurante.utils.Converters.PedidoDTOConvertidor;
import com.restaurante.utils.chainofresponsibility.Cliente.EjecutarClienteHandlerChainResponsability;
import com.restaurante.utils.chainofresponsibility.Plato.EjecutarPlatoHandlerChainResponsability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    private final PedidoServices servicesPedido;
    private final PlatoServices servicesPlato;
    private final ClienteServices clienteServices;

    @Autowired
    public PedidoController(PedidoServices servicesPedido, PlatoServices servicesPlato, ClienteServices clienteServices) {
        this.servicesPedido = servicesPedido;
        this.servicesPlato = servicesPlato;
        this.clienteServices = clienteServices;
    }

    @PostMapping
    public ResponseEntity<ResponderPedidosDTO> agregarPedidos(@RequestBody RecibirPedidosDTO pedidoRecibido) {

        Pedido pedido = PedidoDTOConvertidor.convertirAEntidad(pedidoRecibido);
        Optional<Cliente> clienteOptional = clienteServices.obtenerCliente(pedidoRecibido.getIdCliente());

        EjecutarClienteHandlerChainResponsability ejecutarClienteHandlerChainResponsability = new EjecutarClienteHandlerChainResponsability(servicesPedido, clienteServices);
        ejecutarClienteHandlerChainResponsability.Ejecutar(clienteOptional.get());

        servicesPedido.agregarPedido(pedido, pedidoRecibido.getIdCliente());
        ResponderPedidosDTO respuestaDTO = PedidoDTOConvertidor.convertirDTO(pedido);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponderPedidosDTO> obtenerPedidoPorId(@PathVariable Long id) {
        Pedido pedido = servicesPedido.obtenerPedido(id);
        ResponderPedidosDTO respuestaDTO = PedidoDTOConvertidor.convertirDTO(pedido);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponderPedidosDTO>> listarPedidos() {
        return ResponseEntity.ok(servicesPedido.listarPedido());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            Pedido pedidoActualizado = servicesPedido.actualizarPedido(id, pedido);
            return ResponseEntity.ok("Se ha actualizado exitosamente el pedido");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        servicesPedido.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        try {
            List<Pedido> pedidos = servicesPedido.obtenerPedidosPorCliente(clienteId);
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{pedidoId}/platos")
    public ResponseEntity<Pedido> addPlatosToPedido(@PathVariable Long pedidoId, @RequestBody List<Long> platosIds) {
        Pedido pedido = servicesPedido.obtenerPedido(pedidoId);

        List<Plato> platos = servicesPlato.obtenerTodosPlatoPorId(platosIds);
        if (platos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        EjecutarPlatoHandlerChainResponsability ejecutarPlatoHandlerChainResponsability = new EjecutarPlatoHandlerChainResponsability(servicesPedido, servicesPlato);

        for (Plato plato : platos) {
            ejecutarPlatoHandlerChainResponsability.Ejecutar(plato);
        }

        pedido.getPlatos().addAll(platos);
        pedido.setPrecio(pedido.getPlatos().stream().mapToDouble(Plato::getPrecio).sum());

        Pedido updatedPedido = servicesPedido.agregarPedido(pedido, pedido.getCliente().getId());
        return ResponseEntity.ok(updatedPedido);
    }

    @PostMapping("/calcularPrecio")
    public Double calcularPrecio(@RequestBody Pedido pedido) {
        return servicesPedido.calcularPrecio(pedido);
    }
}
