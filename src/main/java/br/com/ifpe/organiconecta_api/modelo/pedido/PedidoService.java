package br.com.ifpe.organiconecta_api.modelo.pedido;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Transactional
    public Pedido adicionarPedidoCliente(Long clienteId, Pedido pedido) {
        Cliente cliente = clienteService.obterPorID(clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente com ID " + clienteId + " não encontrado.");
        }
        pedido.setCliente(cliente);
        pedido.setHabilitado(Boolean.TRUE);
        return pedidoRepository.save(pedido);
    }

    
    public Pedido obterPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + pedidoId + " não encontrado."));
    }

    @Transactional
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        // Verifica se o cliente existe
        Cliente cliente = clienteService.obterPorID(clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente com ID " + clienteId + " não encontrado.");
        }

        // Busca os pedidos associados ao cliente
        return pedidoRepository.findByCliente(cliente);
    }

    @Transactional
    public void excluirPedidoDeCliente(Long clienteId, Long pedidoId) {
        // Verifica se o cliente existe
        Cliente cliente = clienteService.obterPorID(clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente com ID " + clienteId + " não encontrado.");
        }

        // Busca o pedido e verifica se ele pertence ao cliente
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + pedidoId + " não encontrado."));

        if (!pedido.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException(
                    "Pedido com ID " + pedidoId + " não pertence ao cliente com ID " + clienteId);
        }

        pedido.setHabilitado(Boolean.FALSE);
        pedidoRepository.save(pedido);
    }

}
