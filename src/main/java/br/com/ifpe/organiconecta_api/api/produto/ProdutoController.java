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

import br.com.ifpe.organiconecta_api.modelo.lojas.LojasService;
import br.com.ifpe.organiconecta_api.modelo.produto.Produto;
import br.com.ifpe.organiconecta_api.modelo.produto.ProdutoService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin

@Tag(
    name = "API Produto",
    description = "API responsável pelos serviços de produto no sistema"
)

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private LojasService lojasService;


    @Operation(
        summary = "Serviço responsável por salvar um produto no sistema."
    )
    @PostMapping
        public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {
        Produto produtoNovo = request.build();
        produtoNovo.setLojas(lojasService.obterPorID(request.getIdLojas()));
        Produto produto = produtoService.save(produtoNovo);

        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Serviço responsável por listar os produtos no sistema."
    )
    @GetMapping
        public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @Operation(
        summary = "Serviço responsável por buscar o produto pelo ID no sistema."
    )
    @GetMapping("/{id}")
        public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorId(id);
    }

    @Operation(
        summary = "Serviço responsável por alterar um produto pelo seu ID no sistema."
    )
    @PutMapping("/{id}")
        public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {
            Produto produto = produtoService.obterPorId(id);
            Produto produtoAlterado = request.build();

            produtoAlterado.setId(id);
            produtoAlterado.setLojas(produto.getLojas());

            produtoService.update(id, produtoAlterado);
            return ResponseEntity.ok(produtoAlterado);
    }

    @Operation(
        summary = "Serviço responsável por remover um produto no sistema."
    )
    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Serviço responsável por filtrar os produtos no sistema."
    )
    @PostMapping("/filtrar")
    public List<Produto> filtrar(
        @RequestParam(value = "produtoCodigo", required = false) String produtoCodigo,
        @RequestParam(value = "produtoNome", required = false) String produtoNome,
        @RequestParam(value = "produtoCategoria", required = false) String produtoCategoria) {

        return produtoService.filtrar(produtoCodigo, produtoNome, produtoCategoria);
    }

}