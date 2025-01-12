package br.com.ifpe.organiconecta_api.api.produtor;

import br.com.ifpe.organiconecta_api.modelo.produtor.Produtor;
import br.com.ifpe.organiconecta_api.modelo.produtor.ProdutorService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtor")
public class ProdutorController {

    @Autowired
    private ProdutorService produtorService;

    // Criar produtor
    @PostMapping
    public ResponseEntity<Produtor> save(@RequestBody @Valid Produtor produtor) {
        Produtor novoProdutor = produtorService.save(produtor);
        return new ResponseEntity<>(novoProdutor, HttpStatus.CREATED);
    }

    // Listar todos os produtores
    @GetMapping
    public List<Produtor> listarTodos() {
        return produtorService.listarTodos();
    }

    // Obter produtor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produtor> obterPorID(@PathVariable Long id) {
        Produtor produtor = produtorService.obterPorID(id);
        return ResponseEntity.ok(produtor);
    }

    // Atualizar produtor
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid Produtor produtor) {
        produtorService.update(id, produtor);
        return ResponseEntity.ok().build();
    }

    // Deletar produtor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtorService.delete(id);
        return ResponseEntity.ok().build();
    }
}