package com.restaurante.controllers;

import com.restaurante.dto.clienteDTO.RecibirClienteDTO;
import com.restaurante.dto.clienteDTO.ResponderClienteDTO;
import com.restaurante.models.Cliente;
import com.restaurante.services.ClienteServices;
import com.restaurante.utils.Converters.ClienteDTOConvertidor;
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
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteServices clienteServices;

    @Autowired
    public ClienteController(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    @PostMapping
    public ResponseEntity<ResponderClienteDTO> agregarCliente(@RequestBody RecibirClienteDTO clienteRecibido) {
        Cliente cliente = ClienteDTOConvertidor.convertirAEntidad(clienteRecibido);
        clienteServices.agregarCliente(cliente);
        ResponderClienteDTO respuestaDTO = ClienteDTOConvertidor.convertirDTO(cliente);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponderClienteDTO> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteServices.obtenerCliente(id);
        ResponderClienteDTO respuestaDTO = ClienteDTOConvertidor.convertirDTO(cliente);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteServices.listarClientes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteServices.actualizarCliente(id, cliente);
            return ResponseEntity.ok("Se ha actualizado exitosamente el cliente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteServices.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
