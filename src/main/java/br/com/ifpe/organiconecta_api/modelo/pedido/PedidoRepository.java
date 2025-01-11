package br.com.ifpe.organiconecta_api.modelo.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByCliente(Cliente cliente);

}
