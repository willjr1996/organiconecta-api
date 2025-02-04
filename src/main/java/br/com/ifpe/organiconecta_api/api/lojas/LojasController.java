package br.com.ifpe.organiconecta_api.api.lojas;

import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import br.com.ifpe.organiconecta_api.modelo.lojas.LojasService;
import br.com.ifpe.organiconecta_api.modelo.cliente.ClienteService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/loja")
@CrossOrigin

@Tag(
    name = "API Loja",
    description = "API responsável pelos servidos de lojas no sistema"
)

public class LojasController {

    @Autowired
    private LojasService lojasService;

    @Autowired
    private ClienteService clienteService;


    @Operation(
        summary = "Serviço responsável por salvar uma loja no sistema."
    )
    @PostMapping
    public ResponseEntity<Lojas> save(
            @RequestBody @Valid LojasRequest request)
            {

                Lojas novaLoja = request.build();
                novaLoja.setCliente(clienteService.obterPorID(request.getIdCliente()));
                Lojas loja = lojasService.save(novaLoja);


        
        return new ResponseEntity<Lojas>(loja, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por listar todas as loja no sistema."
    )
    @GetMapping
    public List<Lojas> listarTodos() {
        return lojasService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por buscar loja pelo ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Lojas> obterPorID(@PathVariable Long id) {
        Lojas loja = lojasService.obterPorID(id);
        return ResponseEntity.ok(loja);
    }

    @Operation(
        summary = "Serviço responsável por atualizar loja com associação ao cliente."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable("id") Long id,
        @RequestBody @Valid LojasRequest request) {

        Lojas loja = request.build();
        loja.setCliente(clienteService.obterPorID(request.getIdCliente()));
        lojasService.update(id, loja);

        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por deletar uma loja no sistema."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lojasService.delete(id);
        return ResponseEntity.ok().build();
    }
}
