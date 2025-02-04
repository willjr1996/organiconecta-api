package br.com.ifpe.organiconecta_api.api.endereco;
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

import br.com.ifpe.organiconecta_api.modelo.endereco.EnderecoService;
import br.com.ifpe.organiconecta_api.modelo.endereco.Endereco;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/endereco")
@CrossOrigin

@Tag(
    name = "API Endereco",
    description = "API responsável pelos serviços dos endereços no sistema"
)

public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @Operation(
        summary = "Serviço responsável por salvar um endereço no sistema."
    )
    @PostMapping
        public ResponseEntity<Endereco> save(@RequestBody @Valid EnderecoRequest request) {
        Endereco endereco = enderecoService.save(request.build());
        return new ResponseEntity<Endereco>(endereco, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por listar os endereços no sistema."
    )
    @GetMapping
        public List<Endereco> listarTodos() {
        return enderecoService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por buscar um endereço no sistema.")
    @GetMapping("/{id}")
        public Endereco obterPorID(@PathVariable Long id) {
        return enderecoService.obterPorID(id);
    }

    @Operation(
        summary = "Serviço responsável por alterar um endereço no sistema.")
    @PutMapping("/{id}")
        public ResponseEntity<Endereco> update(@PathVariable("id") Long id, @RequestBody EnderecoRequest request) {
        enderecoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por remover um endereço no sistema.")
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.ok().build();
    }
}