package com.restaurante.utils.Converters;

import com.restaurante.dto.platoDTO.RecibirPlatoDTO;
import com.restaurante.dto.platoDTO.ResponderPlatoDTO;
import com.restaurante.models.Plato;

import java.util.Optional;

public class PlatoDTOConvertidor {
    public static ResponderPlatoDTO convertirDTO(Plato plato){
        ResponderPlatoDTO dto = new ResponderPlatoDTO();

        dto.setId(plato.getId());
        dto.setNombre(plato.getNombre());
        dto.setPrecio(plato.getPrecio());
        dto.setTipoPlato(plato.getTipoPlato());
        return dto;
    }
    public static ResponderPlatoDTO convertirDTO(Optional<Plato> plato){
        ResponderPlatoDTO dto = new ResponderPlatoDTO();

        dto.setId(plato.get().getId());
        dto.setNombre(plato.get().getNombre());
        dto.setPrecio(plato.get().getPrecio());
        dto.setTipoPlato(plato.get().getTipoPlato());
        return dto;
    }
    public static Plato convertirAEntidad(RecibirPlatoDTO dto){
        Plato plato = new Plato();

        plato.setNombre(dto.getNombre());
        plato.setPrecio(dto.getPrecio());
        return plato;
    }
}
