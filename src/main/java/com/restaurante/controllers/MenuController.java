package com.restaurante.controllers;

import com.restaurante.dto.menuDTO.RecibirMenuDTO;
import com.restaurante.dto.menuDTO.ResponderMenuDTO;
import com.restaurante.models.Menu;
import com.restaurante.services.MenuServices;
import com.restaurante.utils.Converters.MenuDTOConvertidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuServices services;

    @Autowired
    public MenuController(MenuServices services) {
        this.services = services;
    }

    @PostMapping
    public ResponseEntity<ResponderMenuDTO> agregarMenu(@RequestBody RecibirMenuDTO menuRecibido) {

        Menu menu = MenuDTOConvertidor.convertirAEntidad(menuRecibido);
        services.agregarMenu(menu);
        ResponderMenuDTO respuestaDTO = MenuDTOConvertidor.convertirDTO(menu);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponderMenuDTO> obtenerMenuPorId(@PathVariable Long id) {
        Optional<Menu> menu = services.obtenerMenu(id);
        ResponderMenuDTO respuestaDTO = MenuDTOConvertidor.convertirDTO(menu);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponderMenuDTO>> listarMenus() {
        return ResponseEntity.ok(services.listarMenu());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarMenu(@PathVariable Long id, @RequestBody Menu menu) {
        try {
            Menu menuActualizado = services.actualizarMenu(id, menu);
            return ResponseEntity.ok("Se ha actualizado exitosamente el menu");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMenu(@PathVariable Long id) {
        services.eliminarMenu(id);
        return ResponseEntity.noContent().build();
    }
}
