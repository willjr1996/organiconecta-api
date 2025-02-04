package br.com.ifpe.organiconecta_api.api.pedido;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import br.com.ifpe.organiconecta_api.api.itemPedido.ItemPedidoRequest;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.itemPedido.ItemPedido;
import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import br.com.ifpe.organiconecta_api.modelo.pedido.PedidoService;
import br.com.ifpe.organiconecta_api.modelo.produto.Produto;
import br.com.ifpe.organiconecta_api.modelo.produto.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin

@Tag(
    name = "API de Pedido",
    description = "API responsável pelos pedidos no sistema"
)

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Operation(
        summary = "Serviço responsável por salvar um pedido no sistema."
    )
    @PostMapping
    public ResponseEntity<Pedido> salvarPedido(@RequestBody @Valid PedidoRequest pedidoRequest) {
        
        System.out.println("PedidoRequest recebido: " + pedidoRequest);

        Cliente cliente = clienteService.obterPorID(pedidoRequest.getIdCliente());

        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoRequest itemPedidoRequest : pedidoRequest.getItens()) {
            Produto produto = produtoService.obterPorId(itemPedidoRequest.getIdProduto());

            if (itemPedidoRequest.getQuantidade() == null || itemPedidoRequest.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero");
            }

            ItemPedido item = ItemPedido.builder()
                .produto(produto)
                .quantidade(itemPedidoRequest.getQuantidade())
                .valorUnitario(itemPedidoRequest.getValorUnitario())
                .build();
            itens.add(item);
        }

        Pedido novoPedido = Pedido.builder()
            .cliente(cliente)
            .itens(itens)
            .build();

        Pedido pedido = pedidoService.salvarPedido(novoPedido);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por consultar o pedido de um cliente no sistema."
    )
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPedidosPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }
    
    @Operation(
        summary = "Serviço responsável por remover um pedido de um determinado cliente no sistema."
    )
    @DeleteMapping("/{pedidoId}/cliente/{clienteId}")
    public ResponseEntity<Void> excluirPedidoDeCliente(
            @PathVariable Long pedidoId,
            @PathVariable Long clienteId) {
        pedidoService.excluirPedidoDeCliente(pedidoId, clienteId);
        return ResponseEntity.noContent().build();
    }

}
