package com.restaurante.restaurante.ulils.converters;



import com.restaurante.restaurante.dto.menuDTO.RecibirMenuDTO;
import com.restaurante.restaurante.dto.menuDTO.ResponderMenuDTO;
import com.restaurante.restaurante.models.Menu;

import java.util.Optional;

public class MenuDTOConvertidor {
    public static ResponderMenuDTO convertirDTO(Menu menu){
        ResponderMenuDTO dto = new ResponderMenuDTO();

        dto.setId(menu.getId());
        dto.setNombre(menu.getNombre());
        dto.setUrlImage(menu.getUrlImage());
        return dto;
    }
    public static ResponderMenuDTO convertirDTO(Optional<Menu> menu){
        ResponderMenuDTO dto = new ResponderMenuDTO();

        dto.setId(menu.get().getId());
        dto.setNombre(menu.get().getNombre());
        dto.setUrlImage(menu.get().getUrlImage());
        return dto;
    }
    public static Menu convertirAEntidad(RecibirMenuDTO dto){
        Menu menu = new Menu();

        menu.setNombre(dto.getNombre());
        menu.setUrlImage(dto.getUrlImage());
        return menu;
    }
}
