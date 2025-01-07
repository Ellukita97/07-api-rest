package com.restaurante.services;

import com.restaurante.constantes.TipoCliente;
import com.restaurante.models.Cliente;
import com.restaurante.repositories.ClienteRepositorio;
import com.restaurante.utils.Observer.Observer;
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

    public void agregarCliente(Cliente cliente) {
        cliente.setTipoCliente(TipoCliente.COMUN);
        repositorio.save(cliente);
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
