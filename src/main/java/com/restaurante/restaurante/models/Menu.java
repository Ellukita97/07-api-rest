package com.restaurante.restaurante.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String urlImage;

    @OneToMany(mappedBy = "menu")
    private List<Plato> platos = new ArrayList<>();

    public Menu() {
    }

    public Menu(Long id, String nombre, String urlImage) {
        this.id = id;
        this.nombre = nombre;
        this.urlImage = urlImage;
    }
}
