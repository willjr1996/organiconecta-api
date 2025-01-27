package br.com.ifpe.organiconecta_api.modelo.pedido;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.itemPedido.ItemPedido;
import br.com.ifpe.organiconecta_api.modelo.itemPedido.ItemPedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired  
    private ClienteService clienteService;

    @Transactional
    public Pedido salvarPedido(Pedido pedido) {
        
        pedido.setDataCompra(LocalDateTime.now());

        Double valorTotal = 0.0;
        for (ItemPedido itemPedido : pedido.getItens()) {
            valorTotal += itemPedido.getValorUnitario() * itemPedido.getQuantidade();
        }

        pedido.setValorTotal(valorTotal);
        pedido.setHabilitado(Boolean.TRUE);
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        for (ItemPedido itemPedido : pedido.getItens()) {
            itemPedido.setPedido(pedidoSalvo);
            itemPedido.setHabilitado(Boolean.TRUE);

            itemPedidoRepository.save(itemPedido);
        }
        
        return pedidoSalvo;
    }
    
    @Transactional
    public Pedido obterPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + pedidoId + " não encontrado."));
    }

    @Transactional
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        Cliente cliente = clienteService.obterPorID(clienteId);
        return pedidoRepository.findByCliente(cliente);
    }

    
    

    // @Transactional
    // public void excluirPedidoDeCliente(Long clienteId, Long pedidoId) {
    //     // Verifica se o cliente existe
    //     Cliente cliente = clienteService.obterPorID(clienteId);
    //     if (cliente == null) {
    //         throw new EntityNotFoundException("Cliente com ID " + clienteId + " não encontrado.");
    //     }

    //     // Busca o pedido e verifica se ele pertence ao cliente
    //     Pedido pedido = pedidoRepository.findById(pedidoId)
    //             .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + pedidoId + " não encontrado."));

    //     if (!pedido.getCliente().getId().equals(clienteId)) {
    //         throw new IllegalArgumentException(
    //                 "Pedido com ID " + pedidoId + " não pertence ao cliente com ID " + clienteId);
    //     }

    //     pedido.setHabilitado(Boolean.FALSE);
    //     pedidoRepository.save(pedido);
    // }

}
