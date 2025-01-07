package com.restaurante.dto.menuDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponderMenuDTO {

        private Long id;
        private String nombre;

        public ResponderMenuDTO() {
        }

        public ResponderMenuDTO(Long id, String nombre) {
                this.id = id;
                this.nombre = nombre;
        }
}