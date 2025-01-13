package br.com.ifpe.organiconecta_api.api.lojas;

import br.com.ifpe.organiconecta_api.modelo.lojas.Lojas;
import br.com.ifpe.organiconecta_api.modelo.lojas.LojasService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lojas")
@CrossOrigin
public class LojasController {

    @Autowired
    private LojasService lojasService;

    // Criar loja com associação ao produtor
    @PostMapping
    public ResponseEntity<Lojas> save(
            @RequestBody @Valid Lojas loja,
            @RequestParam(required = true) Long produtorId) {

        Lojas novaLoja = lojasService.save(loja, produtorId);
        return new ResponseEntity<>(novaLoja, HttpStatus.CREATED);
    }

    // Listar todas as lojas
    @GetMapping
    public List<Lojas> listarTodos() {
        return lojasService.listarTodos();
    }

    // Obter loja por ID
    @GetMapping("/{id}")
    public ResponseEntity<Lojas> obterPorID(@PathVariable Long id) {
        Lojas loja = lojasService.obterPorID(id);
        return ResponseEntity.ok(loja);
    }

    // Atualizar loja com associação ao produtor
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid Lojas loja,
            @RequestParam(required = false) Long produtorId) {

        lojasService.update(id, loja, produtorId);
        return ResponseEntity.ok().build();
    }

    // Deletar loja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lojasService.delete(id);
        return ResponseEntity.ok().build();
    }
}
