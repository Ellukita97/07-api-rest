package com.restaurante.utils.Converters;

import com.restaurante.dto.clienteDTO.RecibirClienteDTO;
import com.restaurante.dto.clienteDTO.ResponderClienteDTO;
import com.restaurante.models.Cliente;

import java.util.Optional;

public class ClienteDTOConvertidor {
    public static ResponderClienteDTO convertirDTO(Cliente cliente){
        ResponderClienteDTO dto = new ResponderClienteDTO();

        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setTipoCliente(cliente.getTipoCliente());
        return dto;
    }
    public static ResponderClienteDTO convertirDTO(Optional<Cliente> cliente){
        ResponderClienteDTO dto = new ResponderClienteDTO();

        dto.setId(cliente.get().getId());
        dto.setNombre(cliente.get().getNombre());
        dto.setEmail(cliente.get().getEmail());
        dto.setTelefono(cliente.get().getTelefono());
        dto.setTipoCliente(cliente.get().getTipoCliente());
        return dto;
    }
    public static Cliente convertirAEntidad(RecibirClienteDTO dto){
        Cliente cliente = new Cliente();

        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }
}
