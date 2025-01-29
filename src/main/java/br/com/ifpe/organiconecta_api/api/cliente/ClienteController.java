package br.com.ifpe.organiconecta_api.api.cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import br.com.ifpe.organiconecta_api.api.pedido.PedidoRequest;
// import br.com.ifpe.organiconecta_api.modelo.cliente.Credenciais;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
// import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteId;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.cliente.EnderecoCliente;
//import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
        public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = clienteService.save(request.build());
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
        public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody ClienteRequest request) {
        clienteService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.ok().build();
    }

    //Cadastrando o pedido do cliente
//     @PostMapping("/pedido/{clienteId}")
//     public ResponseEntity<Pedido> adicionarPedidoCliente(@PathVariable("clienteId") Long clienteId, @RequestBody @Valid PedidoRequest request) {

//        Pedido pedido = clienteService.adicionarPedidoCliente(clienteId, request.build());
//        return new ResponseEntity<Pedido>(pedido, HttpStatus.CREATED);
//    }

   //Excluindo o pedido do cliente
//    @DeleteMapping("/pedido/{pedidoId}")
//    public ResponseEntity<Void> removerPedidoCliente(@PathVariable("pedidoId") Long pedidoId) {

//        clienteService.removerPedidoCliente(pedidoId);
//        return ResponseEntity.noContent().build();
//    }

    //rotas de endereço

    //pegar todos os endereços de um cliente
    @GetMapping("/endereco/{clienteId}")
    public EnderecoCliente obterEnderecoPorID(@PathVariable ("clienteId") Long clienteId) {
        return clienteService.obterEnderecoPorID(clienteId);
    }

    //setar endereço para um cliente específico
    @PostMapping("/endereco/{clienteId}")
    public ResponseEntity<EnderecoCliente> adicionarEnderecoCliente(@PathVariable("clienteId") Long clienteId, @RequestBody @Valid EnderecoClienteRequest request) {
 
        EnderecoCliente endereco = clienteService.adicionarEnderecoCliente(clienteId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.CREATED);
    }
    
    //editar um endereço específico
    @PutMapping("/endereco/{enderecoId}")
    public ResponseEntity<EnderecoCliente> atualizarEnderecoCliente(@PathVariable("enderecoId") Long enderecoId, @RequestBody EnderecoClienteRequest request) {
 
        EnderecoCliente endereco = clienteService.atualizarEnderecoCliente(enderecoId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.OK);
    }
   
    //deletar um endereço específico
    @DeleteMapping("/endereco/{enderecoId}")
    public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("enderecoId") Long enderecoId) {
 
        clienteService.removerEnderecoCliente(enderecoId);
        return ResponseEntity.noContent().build();
    }
}