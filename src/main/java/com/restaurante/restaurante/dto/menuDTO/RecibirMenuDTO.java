package com.restaurante.restaurante.dto.menuDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecibirMenuDTO {

    private String nombre;
    private String urlImage;

    public RecibirMenuDTO() {
    }

    public RecibirMenuDTO(String nombre, String urlImage) {
        this.nombre = nombre;
        this.urlImage = urlImage;
    }
}
