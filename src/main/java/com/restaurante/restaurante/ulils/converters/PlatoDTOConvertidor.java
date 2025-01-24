package com.restaurante.restaurante.ulils.converters;



import com.restaurante.restaurante.dto.platoDTO.RecibirPlatoDTO;
import com.restaurante.restaurante.dto.platoDTO.ResponderPlatoDTO;
import com.restaurante.restaurante.models.Plato;

import java.util.Optional;

public class PlatoDTOConvertidor {
    public static ResponderPlatoDTO convertirDTO(Plato plato){
        ResponderPlatoDTO dto = new ResponderPlatoDTO();

        dto.setId(plato.getId());
        dto.setNombre(plato.getNombre());
        dto.setPrecio(plato.getPrecio());
        dto.setTipoPlato(plato.getTipoPlato());
        dto.setUrlImage(plato.getUrlImage());
        return dto;
    }
    public static ResponderPlatoDTO convertirDTO(Optional<Plato> plato){
        ResponderPlatoDTO dto = new ResponderPlatoDTO();

        dto.setId(plato.get().getId());
        dto.setNombre(plato.get().getNombre());
        dto.setPrecio(plato.get().getPrecio());
        dto.setTipoPlato(plato.get().getTipoPlato());
        dto.setUrlImage(plato.get().getUrlImage());
        return dto;
    }
    public static Plato convertirAEntidad(RecibirPlatoDTO dto){
        Plato plato = new Plato();

        plato.setNombre(dto.getNombre());
        plato.setPrecio(dto.getPrecio());
        plato.setUrlImage(dto.getUrlImage());
        return plato;
    }
}
