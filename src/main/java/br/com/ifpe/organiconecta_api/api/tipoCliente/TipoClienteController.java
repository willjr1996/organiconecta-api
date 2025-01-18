package br.com.ifpe.organiconecta_api.api.tipoCliente;


import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteService;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/tipocliente")
public class TipoClienteController {


    @Autowired
    private TipoClienteService tipoClienteService;


   @Autowired
   private ClienteService clienteService;




    // Criar produtor com associação à loja
    @PostMapping
    public ResponseEntity<TipoCliente> save(
            @RequestBody @Valid TipoClienteRequest request) {


        TipoCliente novoTipoCliente = request.build();
        novoTipoCliente.setCliente(clienteService.obterPorID(request.getClienteId()));
        TipoCliente tipoCliente = tipoClienteService.save(novoTipoCliente);


        return new ResponseEntity<>(tipoCliente, HttpStatus.CREATED);
    }


    // Listar todos os produtores
    @GetMapping
    public List<TipoCliente> listarTodos() {
        return tipoClienteService.listarTodos();
    }


    // Obter produtor por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoCliente> obterPorID(@PathVariable Long id) {
        TipoCliente tipoCliente = tipoClienteService.obterPorID(id);
        return ResponseEntity.ok(tipoCliente);
    }


    // Atualizar tipoCliente com associação à loja
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody @Valid TipoClienteRequest request) {


                TipoCliente tipoCliente = request.build();
                tipoCliente.setCliente (clienteService.obterPorID(request.getClienteId()));
                tipoClienteService.update(id, tipoCliente);
         
        return ResponseEntity.ok().build();
    }


   


    // Deletar tipoCliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoClienteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
