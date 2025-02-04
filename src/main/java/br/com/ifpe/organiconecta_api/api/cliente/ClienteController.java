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

import br.com.ifpe.organiconecta_api.modelo.acesso.UsuarioService;
//import br.com.ifpe.organiconecta_api.api.pedido.PedidoRequest;
// import br.com.ifpe.organiconecta_api.modelo.cliente.Credenciais;
import br.com.ifpe.organiconecta_api.modelo.cliente.Cliente;
// import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteId;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.cliente.EnderecoCliente;
//import br.com.ifpe.organiconecta_api.modelo.pedido.Pedido;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin

@Tag(
    name = "API Cliente",
    description = "API responsável pelos serços de Cliente no sistema"
)

public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Serviço responsável por salvar um cliente no sistema."
    )
    @PostMapping
        public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest request) {
        Cliente cliente = clienteService.save(request.build());
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }
    @Operation(
        summary = "Serviço responsável por listar os clientes no sistema."
    )
    @GetMapping
        public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por buscar um cliente pelo ID no sistema."
    )
    @GetMapping("/{id}")
        public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id);
    }

    @Operation(
        summary = "Serviço responsável por alterar um cliente no sistema."
    )
    @PutMapping("/{id}")
        public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody ClienteRequest request) {
        clienteService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por deletar um cliente no sistema."
    )
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    //rotas de endereço

    @Operation(
        summary = "Serviço responsável por listar todos os endereços de um cliente no sistema."
    )
    @GetMapping("/endereco/{clienteId}")
    public EnderecoCliente obterEnderecoPorID(@PathVariable ("clienteId") Long clienteId) {
        return clienteService.obterEnderecoPorID(clienteId);
    }

    @Operation(
        summary = "Serviço responsável por cadastrar endereço para um cliente."
    )
    @PostMapping("/endereco/{clienteId}")
    public ResponseEntity<EnderecoCliente> adicionarEnderecoCliente(@PathVariable("clienteId") Long clienteId, @RequestBody @Valid EnderecoClienteRequest request) {

        EnderecoCliente endereco = clienteService.adicionarEnderecoCliente(clienteId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por atualizar o endereço de um cliente pelo seu ID."
    )
    @PutMapping("/endereco/{enderecoId}")
    public ResponseEntity<EnderecoCliente> atualizarEnderecoCliente(@PathVariable("enderecoId") Long enderecoId, @RequestBody EnderecoClienteRequest request) {

        EnderecoCliente endereco = clienteService.atualizarEnderecoCliente(enderecoId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.OK);
    }


    @Operation(
        summary = "Serviço responsável por deletar o endereço de um cliente."
    )
    //deletar um endereço específico
    @DeleteMapping("/endereco/{enderecoId}")
    public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("enderecoId") Long enderecoId) {

        clienteService.removerEnderecoCliente(enderecoId);
        return ResponseEntity.noContent().build();
    }
}