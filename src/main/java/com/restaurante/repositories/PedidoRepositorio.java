package com.restaurante.repositories;

import com.restaurante.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente_Id(Long clienteId);
    long countByClienteId(Long clienteId);

    List<Pedido> findByClienteId(Long clienteId);

    @Query("SELECT COUNT(pp) FROM Pedido p " +
            "JOIN p.platos pp " +
            "WHERE pp.id = :platoId")
    long contarVecesQuePlatoFuePedido(@Param("platoId") Long platoId);
}
