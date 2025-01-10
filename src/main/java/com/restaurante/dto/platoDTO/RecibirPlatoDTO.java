package com.restaurante.dto.platoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecibirPlatoDTO {

    private String nombre;
    private Long idMenu;
    private Double precio;

    public RecibirPlatoDTO() {
    }

    public RecibirPlatoDTO(String nombre, Long idMenu, Double precio) {
        this.nombre = nombre;
        this.idMenu = idMenu;
        this.precio = precio;
    }
}
