package com.restaurante.restaurante.dto.menuDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponderMenuDTO {

        private Long id;
        private String nombre;
        private String urlImage;

        public ResponderMenuDTO() {
        }

        public ResponderMenuDTO(Long id, String nombre, String urlImage) {
                this.id = id;
                this.nombre = nombre;
                this.urlImage = urlImage;
        }
}