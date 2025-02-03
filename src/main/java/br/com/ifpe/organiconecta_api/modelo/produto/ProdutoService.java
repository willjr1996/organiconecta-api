package br.com.ifpe.organiconecta_api.modelo.produto;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto save(Produto produto) {
        
        produto.setHabilitado(Boolean.TRUE);
        return repository.save(produto);

    }

    public List<Produto> listarTodos() {
        
        return repository.findAll();

    }

    public Produto obterPorId(Long id) {
        
        return repository.findById(id).get();
        
    }

   @Transactional
    public void update(Long id, Produto produtoAlterado) {
    Produto produto = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado."));

    produto.setProdutoNome(produtoAlterado.getProdutoNome());
    produto.setProdutoDescricao(produtoAlterado.getProdutoDescricao());
    produto.setProdutoImagem(produtoAlterado.getProdutoImagem());
    produto.setProdutoCategoria(produtoAlterado.getProdutoCategoria());
    produto.setProdutoCodigo(produtoAlterado.getProdutoCodigo());
    
    // Vai Atualizar apenas a quantidade quando necessário
    if (produtoAlterado.getProdutoQuantidade() != null) {
        produto.setProdutoQuantidade(produtoAlterado.getProdutoQuantidade());
    }

    repository.save(produto);
}


    @Transactional  
    public void delete(Long id) {
        Produto produto = repository.findById(id).get();
        produto.setHabilitado(Boolean.FALSE);
        repository.save(produto);

    }

    public List<Produto> filtrar(String produtoCodigo, String produtoNome, String produtoCategoria) {

    List<Produto> listaProdutos = repository.findAll();

    if ((produtoCodigo != null && !"".equals(produtoCodigo)) &&
        (produtoNome == null || "".equals(produtoNome)) &&
        (produtoCategoria == null)) {
            listaProdutos = repository.findByProdutoCodigoContainingIgnoreCaseOrderByProdutoCodigoAsc(produtoCodigo);
    } else if (
        (produtoCodigo == null || "".equals(produtoCodigo)) &&
        (produtoNome != null && !"".equals(produtoNome)) &&
        (produtoCategoria == null)) {    
            listaProdutos = repository.findByProdutoNomeContainingIgnoreCaseOrderByProdutoNomeAsc(produtoNome);
    } else if (
        (produtoCodigo == null || "".equals(produtoCodigo)) &&
        (produtoNome == null || "".equals(produtoNome)) &&
        (produtoCategoria != null)) {
            listaProdutos = repository.findByProdutoCategoriaContainingIgnoreCaseOrderByProdutoCategoriaAsc(produtoCategoria); 
    } else if (
        (produtoCodigo == null || "".equals(produtoCodigo)) &&
        (produtoNome != null && !"".equals(produtoNome)) &&
        (produtoCategoria != null)) {
            listaProdutos = repository.consultarPorProdutoNomeECategoria(produtoNome, produtoCategoria); 
    } else if (
        (produtoCodigo != null || "".equals(produtoCodigo)) &&
        (produtoNome != null && !"".equals(produtoNome)) &&
        (produtoCategoria != null)) {
            listaProdutos = repository.consultarPorProdutoNomeECategoriaECodigo(produtoCodigo, produtoNome, produtoCategoria); 
    } 

    return listaProdutos;
    }

}