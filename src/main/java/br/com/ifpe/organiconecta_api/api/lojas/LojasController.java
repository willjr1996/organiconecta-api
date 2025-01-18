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
@RequestMapping("/api/loja")
@CrossOrigin
public class LojasController {

    @Autowired
    private LojasService lojasService;

    @PostMapping
    public ResponseEntity<Lojas> save(
            @RequestBody @Valid Lojas loja,
            @RequestParam(required = true) Long clienteId) {

        Lojas novaLoja = lojasService.save(loja, clienteId);
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

    // Atualizar loja com associação ao cliente
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid Lojas loja,
            @RequestParam(required = false) Long clienteId) {

        lojasService.update(id, loja, clienteId);
        return ResponseEntity.ok().build();
    }

    // Deletar loja
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lojasService.delete(id);
        return ResponseEntity.ok().build();
    }
}
