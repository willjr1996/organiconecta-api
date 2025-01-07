package br.com.ifpe.organiconecta_api.api.pedido;

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

import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import br.com.ifpe.organiconecta_api.modelo.pedido.PedidoService;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    //Cria um pedido para o cliente
    @PostMapping("/{clienteId}")
    public ResponseEntity<Pedido> criarPedido(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.adicionarPedidoCliente(clienteId, pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    //Consultar pedido por pedidoId
    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> obterPedido(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.obterPorId(pedidoId);
        return ResponseEntity.ok(pedido);
    }

    //Consultando todos os pedidos do cliente pelo ClienteID
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPedidosPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    //Remove o pedido pelo IdProduto, se pertencer ao cliente determinado pelo ClienteoID
    @DeleteMapping("/cliente/{clienteId}/pedido/{pedidoId}")
    public ResponseEntity<Void> excluirPedidoDeCliente(
            @PathVariable Long clienteId,
            @PathVariable Long pedidoId) {
        pedidoService.excluirPedidoDeCliente(clienteId, pedidoId);
        return ResponseEntity.noContent().build();
    }

}
