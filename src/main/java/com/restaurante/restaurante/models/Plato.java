package com.restaurante.restaurante.models;

import com.restaurante.restaurante.constantes.TipoPlato;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;

    @Enumerated(EnumType.STRING)
    private TipoPlato tipoPlato;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Plato() {
    }

    public Plato(Long id, String nombre, Double precio, TipoPlato tipoPlato, Menu menu) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipoPlato = tipoPlato;
        this.menu = menu;
    }
}
