package com.restaurante.restaurante.dto.platoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecibirPlatoDTO {

    private String nombre;
    private Long idMenu;
    private Double precio;
    private String urlImage;

    public RecibirPlatoDTO() {
    }

    public RecibirPlatoDTO(String urlImage, Double precio, Long idMenu, String nombre) {
        this.urlImage = urlImage;
        this.precio = precio;
        this.idMenu = idMenu;
        this.nombre = nombre;
    }
}
