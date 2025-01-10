package com.restaurante.restaurante.services;

import com.restaurante.restaurante.constantes.TipoCliente;
import com.restaurante.restaurante.models.Cliente;
import com.restaurante.restaurante.patronesDeDisenio.Observer.Observer;
import com.restaurante.restaurante.repositories.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {
    private final ClienteRepositorio repositorio;
    private List<Observer> observers = new ArrayList<>();

    @Autowired
    public ClienteServices(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente agregarCliente(Cliente cliente) {
        cliente.setTipoCliente(TipoCliente.COMUN);
        return repositorio.save(cliente);
    }

    public Optional<Cliente> obtenerCliente(Long id) {
        return repositorio.findById(id);
    }

    public List<Cliente> listarClientes() {
        return repositorio.findAll();
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return repositorio.findById(id).map(prop -> {
            prop.setNombre(clienteActualizado.getNombre());
            prop.setEmail(clienteActualizado.getEmail());
            prop.setTelefono(clienteActualizado.getTelefono());
            prop.setTipoCliente(clienteActualizado.getTipoCliente());
            return repositorio.save(prop);
        }).orElseThrow(() -> new RuntimeException("Cliente con el id " + id + " no pudo ser actualizado"));
    }

    public void eliminarCliente(Long id) {
        repositorio.deleteById(id);
    }

}
