package com.restaurante.dto.clienteDTO;

import com.restaurante.constantes.TipoCliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponderClienteDTO {

        private Long id;
        private String nombre;
        private String email;
        private String telefono;
        private TipoCliente tipoCliente;

}