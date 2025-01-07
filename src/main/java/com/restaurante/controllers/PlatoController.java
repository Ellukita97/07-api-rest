package com.restaurante.controllers;

import com.restaurante.dto.platoDTO.RecibirPlatoDTO;
import com.restaurante.dto.platoDTO.ResponderPlatoDTO;
import com.restaurante.models.Menu;
import com.restaurante.models.Plato;
import com.restaurante.services.MenuServices;
import com.restaurante.services.PlatoServices;
import com.restaurante.utils.Converters.PlatoDTOConvertidor;
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
@RequestMapping("/api/platos")
public class PlatoController {
    private final PlatoServices servicesPlato;
    private final MenuServices servicesMenu;

    @Autowired
    public PlatoController(PlatoServices servicesPlato, MenuServices servicesMenu) {
        this.servicesPlato = servicesPlato;
        this.servicesMenu = servicesMenu;
    }

    @PostMapping
    public ResponseEntity<ResponderPlatoDTO> agregarPlato(@RequestBody RecibirPlatoDTO platoRecibido) {
        Optional<Menu> menuOpcional = servicesMenu.obtenerMenu(platoRecibido.getIdMenu());
        if (!menuOpcional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Plato plato = PlatoDTOConvertidor.convertirAEntidad(platoRecibido);
        plato.setMenu(menuOpcional.get());
        servicesPlato.agregarPlato(plato);
        ResponderPlatoDTO respuestaDTO = PlatoDTOConvertidor.convertirDTO(plato);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponderPlatoDTO> obtenerPlatoPorId(@PathVariable Long id) {
        Optional<Plato> plato = servicesPlato.obtenerPlato(id);
        ResponderPlatoDTO respuestaDTO = PlatoDTOConvertidor.convertirDTO(plato);
        return ResponseEntity.ok(respuestaDTO);
    }

    @GetMapping
    public ResponseEntity<List<ResponderPlatoDTO>> listarPlato() {
        return ResponseEntity.ok(servicesPlato.listarPlato());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPlato(@PathVariable Long id, @RequestBody Plato plato) {
        try {
            Plato platoActualizado = servicesPlato.actualizarPlato(id, plato);
            return ResponseEntity.ok("Se ha actualizado exitosamente el plato");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Long id) {
        servicesPlato.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }
}
