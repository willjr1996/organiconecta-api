package br.com.ifpe.organiconecta_api.api.tipoCliente;

import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoCliente;
import br.com.ifpe.organiconecta_api.modelo.tipoCliente.TipoClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tipocliente")
@Tag(
    name = "API Tipo Cliente",
    description = "API responsável pelos serviços de Tipo cliente no sistema"
)

public class TipoClienteController {

    @Autowired
    private TipoClienteService tipoClienteService;

    @Operation(
        summary = "Serviço responsável por criar produtor com associação à loja no sistema."
    )
    @PostMapping
    public ResponseEntity<TipoCliente> save(
            @RequestBody @Valid TipoClienteRequest request) {


        TipoCliente novoTipoCliente = request.build();
        TipoCliente tipoCliente = tipoClienteService.save(novoTipoCliente);


        return new ResponseEntity<>(tipoCliente, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por Listar todos os produtores no sistema."
    )
    // 
    @GetMapping
    public List<TipoCliente> listarTodos() {
        return tipoClienteService.listarTodos();
    }
    @Operation(
        summary = "Serviço responsável por obter produtor por ID no sistema."
    )
    @GetMapping("/{id}")
    public ResponseEntity<TipoCliente> obterPorID(@PathVariable Long id) {
        TipoCliente tipoCliente = tipoClienteService.obterPorID(id);
        return ResponseEntity.ok(tipoCliente);
    }

    // Atualizar tipoCliente com associação à loja
    // @PutMapping("/{id}")
    // public ResponseEntity<Void> update(
    //         @PathVariable Long id,
    //         @RequestBody @Valid TipoClienteRequest request) {


    //             TipoCliente tipoCliente = request.build();
    //             tipoCliente.setCliente (clienteService.obterPorID(request.getClienteId()));
    //             tipoClienteService.update(id, tipoCliente);
         
    //     return ResponseEntity.ok().build();
    // }

    @Operation(
        summary = "Serviço responsável por deletar o tipoCliente no sistema."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoClienteService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por inicializar o tipoCliente no sistema."
    )
    @PostMapping("/inicializar")
    public ResponseEntity<Void> inicializarTipos() {
        tipoClienteService.inicializarTipos();
        return ResponseEntity.ok().build();
    }
}
