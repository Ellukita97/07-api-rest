package com.restaurante.dto.menuDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecibirMenuDTO {

    private String nombre;

    public RecibirMenuDTO() {
    }

    public RecibirMenuDTO(String nombre) {
        this.nombre = nombre;
    }
}
