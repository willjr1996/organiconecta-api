package br.com.ifpe.organiconecta_api.api.produto;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.organiconecta_api.modelo.produto.Produto;
import br.com.ifpe.organiconecta_api.modelo.produto.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
        public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {
        Produto produto = produtoService.save(request.build());
        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
        public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorId(id);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {
        produtoService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filtrar")
    public List<Produto> filtrar(
        @RequestParam(value = "produtoCodigo", required = false) String produtoCodigo,
        @RequestParam(value = "produtoNome", required = false) String produtoNome,
        @RequestParam(value = "produtoCategoria", required = false) String produtoCategoria) {

        return produtoService.filtrar(produtoCodigo, produtoNome, produtoCategoria);
    }

}
