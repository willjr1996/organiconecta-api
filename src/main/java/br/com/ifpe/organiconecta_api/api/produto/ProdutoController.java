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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.organiconecta_api.modelo.produto.FirebaseService;
import br.com.ifpe.organiconecta_api.modelo.produto.Produto;
import br.com.ifpe.organiconecta_api.modelo.produto.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FirebaseService firebaseService;

    // 🔹 Criar um Produto (POST) com Upload de Imagem 🔹
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Produto> save(
            @Valid
            @RequestParam("produtoNome") String produtoNome,
            @RequestParam("produtoDescricao") String produtoDescricao,
            @RequestParam("produtoPreco") double produtoPreco,
            @RequestParam("produtoCategoria") String produtoCategoria,
            @RequestParam("produtoCodigo") String produtoCodigo,
            @RequestPart("produtoImagem") MultipartFile produtoImagem) {

        // Upload da imagem no Firebase
        String imageUrl = firebaseService.uploadFile(produtoImagem);

        Produto produto = Produto.builder()
                .produtoNome(produtoNome)
                .produtoDescricao(produtoDescricao)
                .produtoPreco(produtoPreco)
                .produtoCategoria(produtoCategoria)
                .produtoCodigo(produtoCodigo)
                .produtoImagem(imageUrl) // Salva a URL da imagem
                .build();

        Produto savedProduto = produtoService.save(produto);
        return new ResponseEntity<>(savedProduto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorId(id);
    }

    // 🔹 Atualizar um Produto (PUT) com Upload de Imagem 🔹
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Produto> update(
            @Valid
            @PathVariable("id") Long id,
            @RequestParam("produtoNome") String produtoNome,
            @RequestParam("produtoDescricao") String produtoDescricao,
            @RequestParam("produtoPreco") double produtoPreco,
            @RequestParam("produtoCategoria") String produtoCategoria,
            @RequestParam("produtoCodigo") String produtoCodigo,
            @RequestPart(value = "produtoImagem", required = false) MultipartFile produtoImagem) {

        Produto produto = produtoService.obterPorId(id);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza os dados
        produto.setProdutoNome(produtoNome);
        produto.setProdutoDescricao(produtoDescricao);
        produto.setProdutoPreco(produtoPreco);
        produto.setProdutoCategoria(produtoCategoria);
        produto.setProdutoCodigo(produtoCodigo);

        // Se uma nova imagem for enviada, substitui a anterior
        if (produtoImagem != null && !produtoImagem.isEmpty()) {
            if (produto.getProdutoImagem() != null) {
                firebaseService.deleteFile(produto.getProdutoImagem()); // Exclui a imagem antiga
            }
            String imageUrl = firebaseService.uploadFile(produtoImagem); // Upload da nova imagem
            produto.setProdutoImagem(imageUrl);
        }

        produtoService.update(id, produto);
        return ResponseEntity.ok(produto);
    }

    // 🔹 Excluir um Produto e a Imagem associada (DELETE) 🔹
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Produto produto = produtoService.obterPorId(id);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        // Remove a imagem do Firebase antes de excluir o produto
        if (produto.getProdutoImagem() != null) {
            firebaseService.deleteFile(produto.getProdutoImagem());
        }

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
