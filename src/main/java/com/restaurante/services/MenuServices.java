package com.restaurante.services;

import com.restaurante.dto.menuDTO.ResponderMenuDTO;
import com.restaurante.dto.pedidosDTO.ResponderPedidosDTO;
import com.restaurante.models.Menu;
import com.restaurante.repositories.MenuRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServices {
    private final MenuRepositorio repositorio;

    @Autowired
    public MenuServices(MenuRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void agregarMenu(Menu menu) {
        repositorio.save(menu);
    }

    public Optional<Menu> obtenerMenu(Long id) {
        return repositorio.findById(id);
    }

    public List<ResponderMenuDTO> listarMenu() {

        return repositorio.findAll().stream()
                .map(plato -> new ResponderMenuDTO(
                        plato.getId(),
                        plato.getNombre()
                ))
                .collect(Collectors.toList());
    }

    public Menu actualizarMenu(Long id, Menu menuActualizado) {
        return repositorio.findById(id).map(prop -> {
            prop.setNombre(menuActualizado.getNombre());
            prop.setPlatos(menuActualizado.getPlatos());
            return repositorio.save(prop);
        }).orElseThrow(() -> new RuntimeException("Menu con el id " + id + " no pudo ser actualizado"));
    }

    public void eliminarMenu(Long id) {
        repositorio.deleteById(id);
    }

}
