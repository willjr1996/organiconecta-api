package br.com.ifpe.organiconecta_api.modelo.produto;
import java.util.List;
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

    public Produto obterPorID(Long id) {
        
        return repository.findById(id).get();
        
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado) {
        
        Produto produto = repository.findById(id).get();
        produto.setProdutoNome(produtoAlterado.getProdutoNome());
        produto.setProdutoDescricao(produtoAlterado.getProdutoDescricao());
        produto.setProdutoImagem(produtoAlterado.getProdutoImagem());
        produto.setProdutoImagem(produtoAlterado.getProdutoImagem());
        produto.setProdutoCategoria(produtoAlterado.getProdutoCategoria());
        repository.save(produto);
    }

    @Transactional  
    public void delete(Long id) {
        Produto produto = repository.findById(id).get();
        produto.setHabilitado(Boolean.FALSE);
        repository.save(produto);

   }
}
