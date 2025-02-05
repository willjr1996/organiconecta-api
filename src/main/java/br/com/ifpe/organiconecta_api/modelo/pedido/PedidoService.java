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
import br.com.ifpe.organiconecta_api.modelo.pagamento.Pagamento;
import br.com.ifpe.organiconecta_api.modelo.pagamento.PagamentoService;
import br.com.ifpe.organiconecta_api.modelo.produto.Produto;
import br.com.ifpe.organiconecta_api.modelo.produto.ProdutoService;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Transactional
    public Pedido salvarPedido(Pedido pedido) {

        pedido.setDataCompra(LocalDateTime.now());

        Double valorTotal = 0.0;

        for (ItemPedido itemPedido : pedido.getItens()) {
            // Busca o produto mais atualizado do banco
            Produto produto = produtoService.obterPorId(itemPedido.getProduto().getId());

            // Verifica se há estoque suficiente
            if (produto.getProdutoQuantidade() < itemPedido.getQuantidade()) {
                throw new IllegalArgumentException(
                        "Quantidade insuficiente para o produto: " + produto.getProdutoNome());
            }

            // Desconta a quantidade do estoque
            produto.setProdutoQuantidade(produto.getProdutoQuantidade() - itemPedido.getQuantidade());
            produtoService.update(produto.getId(), produto);

            // Calcula o valor total
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

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedidoSalvo);
        pagamento.setPagamentoFeito(Boolean.TRUE);
        pagamentoService.save(pagamento);

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

    @Transactional
    public void excluirPedidoDeCliente(Long pedidoId, Long clienteId) {
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
