package com.restaurante.models;

import com.restaurante.constantes.TipoCliente;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    @Enumerated(EnumType.STRING)  // Almacena el nombre del enum como texto
    private TipoCliente tipoCliente;

    public Cliente() {
    }

    public Cliente(Long id, String nombre, String email, String telefono, TipoCliente tipoCliente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.tipoCliente = tipoCliente;
    }
}
