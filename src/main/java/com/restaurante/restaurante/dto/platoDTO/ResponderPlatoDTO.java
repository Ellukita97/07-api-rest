package com.restaurante.restaurante.dto.platoDTO;

import com.restaurante.restaurante.constantes.TipoPlato;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponderPlatoDTO {

        private Long id;
        private String nombre;
        private Double precio;
        private String urlImage;
        private TipoPlato tipoPlato;
        private Long Idmenu;

        public ResponderPlatoDTO() {
        }

        public ResponderPlatoDTO(Long id, String nombre, Double precio, TipoPlato tipoPlato, Long idmenu, String urlImage) {
                this.id = id;
                this.nombre = nombre;
                this.precio = precio;
                this.tipoPlato = tipoPlato;
                Idmenu = idmenu;
                this.urlImage = urlImage;
        }
}