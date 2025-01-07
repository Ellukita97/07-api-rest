package com.restaurante.utils.Converters;

import com.restaurante.dto.menuDTO.RecibirMenuDTO;
import com.restaurante.dto.menuDTO.ResponderMenuDTO;
import com.restaurante.models.Menu;

import java.util.Optional;

public class MenuDTOConvertidor {
    public static ResponderMenuDTO convertirDTO(Menu menu){
        ResponderMenuDTO dto = new ResponderMenuDTO();

        dto.setId(menu.getId());
        dto.setNombre(menu.getNombre());
        return dto;
    }
    public static ResponderMenuDTO convertirDTO(Optional<Menu> menu){
        ResponderMenuDTO dto = new ResponderMenuDTO();

        dto.setId(menu.get().getId());
        dto.setNombre(menu.get().getNombre());
        return dto;
    }
    public static Menu convertirAEntidad(RecibirMenuDTO dto){
        Menu menu = new Menu();

        menu.setNombre(dto.getNombre());
        return menu;
    }
}
