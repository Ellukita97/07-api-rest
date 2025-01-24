package com.restaurante.restaurante.services;

import com.restaurante.restaurante.constantes.TipoPlato;
import com.restaurante.restaurante.dto.platoDTO.ResponderPlatoDTO;
import com.restaurante.restaurante.models.Plato;
import com.restaurante.restaurante.repositories.PlatoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatoServices {
    private final PlatoRepositorio repositorio;

    @Autowired
    public PlatoServices(PlatoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Plato agregarPlato(Plato plato) {
        plato.setTipoPlato(TipoPlato.COMUN);
        return repositorio.save(plato);
    }

    public Optional<Plato> obtenerPlato(Long id) {
        return repositorio.findById(id);
    }

    public List<Plato> obtenerTodosPlatoPorId(List<Long> ids) {
        return repositorio.findAllById(ids);
    }

    public List<ResponderPlatoDTO> listarPlato() {
        return repositorio.findAll().stream()
                .map(plato -> new ResponderPlatoDTO(
                        plato.getId(),
                        plato.getNombre(),
                        plato.getPrecio(),
                        plato.getTipoPlato(),
                        plato.getMenu().getId(),
                        plato.getUrlImage()
                ))
                .collect(Collectors.toList());
    }

    public Plato actualizarPlato(Long id, Plato platoActualizado) {
        return repositorio.findById(id).map(prop -> {
            prop.setNombre(platoActualizado.getNombre());
            prop.setMenu(platoActualizado.getMenu());
            prop.setTipoPlato(platoActualizado.getTipoPlato());
            return repositorio.save(prop);
        }).orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no pudo ser actualizado"));
    }

    public void eliminarPlato(Long id) {
        repositorio.deleteById(id);
    }

}
